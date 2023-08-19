package vn.dangdnh.service.identity.impl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import vn.dangdnh.definition.ConstantValues;
import vn.dangdnh.definition.CryptoAlgorithm;
import vn.dangdnh.definition.DefaultRole;
import vn.dangdnh.definition.TimeZones;
import vn.dangdnh.definition.message.exception.ExceptionMessages;
import vn.dangdnh.dto.request.token.JwtTokenRenewRequestCommand;
import vn.dangdnh.dto.request.token.JwtTokenValidationRequestCommand;
import vn.dangdnh.dto.request.user.UserSignInRequest;
import vn.dangdnh.dto.request.user.UserSignUpRequest;
import vn.dangdnh.dto.response.JwtToken;
import vn.dangdnh.dto.response.TokenDetails;
import vn.dangdnh.dto.response.TokenValidationResult;
import vn.dangdnh.dto.role.RoleDto;
import vn.dangdnh.dto.user.UserDto;
import vn.dangdnh.exception.AuthenticationException;
import vn.dangdnh.exception.DataConflictException;
import vn.dangdnh.exception.EntityAlreadyExistsException;
import vn.dangdnh.exception.EntityNotFoundException;
import vn.dangdnh.model.role.Role;
import vn.dangdnh.model.user.User;
import vn.dangdnh.repository.role.RoleRepository;
import vn.dangdnh.repository.user.UserRepository;
import vn.dangdnh.security.utils.JwtUtils;
import vn.dangdnh.service.identity.RoleService;
import vn.dangdnh.service.identity.TokenService;
import vn.dangdnh.service.identity.UserService;
import org.modelmapper.ModelMapper;
import org.passay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class IdentityAccessManagementImpl implements UserService, TokenService, RoleService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public IdentityAccessManagementImpl(UserRepository userRepository, RoleRepository roleRepository,
                                        JwtUtils jwtUtils, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto signUp(UserSignUpRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new EntityAlreadyExistsException(ExceptionMessages.USERNAME_ALREADY_EXISTS);
        }
        // Validate username
        validateUsernamePattern(request.getUsername());
        // Validate password
        validatePasswordPattern(request.getPassword());
        // Create user details
        User user = new User();
        String salt = BCrypt.gensalt();
        String encryptedPassword = BCrypt.hashpw(request.getPassword(), salt);
        user.setUsername(request.getUsername());
        user.setPassword(encryptedPassword);
        user.setIsEnabled(true);
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsCredentialsNonExpired(true);
        Set<String> authorities = new HashSet<>();
        authorities.add(DefaultRole.ROLE_USER.name());
        user.setAuthorities(authorities);
        user.setCryptoAlgorithm(CryptoAlgorithm.BCRYPT);
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of(TimeZones.VIETNAM));
        user.setCreatedAt(zdt);
        user.setUpdatedAt(zdt);
        user = userRepository.insert(user);
        return user.toDto();
    }

    @Override
    public TokenDetails signIn(UserSignInRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AuthenticationException(ExceptionMessages.Security.WRONG_USERNAME));
        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new AuthenticationException(ExceptionMessages.Security.WRONG_PASSWORD);
        }
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        org.springframework.security.core.userdetails.User userSecurity = new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), grantedAuthorities);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userSecurity, null, grantedAuthorities);
        ZonedDateTime datetime = ZonedDateTime.now(ZoneId.of(TimeZones.VIETNAM));
        user.setLastLogin(datetime);
        user = userRepository.save(user);
        String jwtToken = jwtUtils.generateToken(authenticationToken);
        return TokenDetails.builder()
                .username(user.getUsername())
                .accessToken(jwtToken)
                .build();
    }

    @PreAuthorize ("hasRole('ROLE_ADMIN')")
    @Override
    public RoleDto findRoleById(String id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessages.ENTITY_NOT_FOUND));
        return role.toDto();
    }

    @PreAuthorize ("hasRole('ROLE_ADMIN')")
    @Override
    public RoleDto createRole(RoleDto dto) {
        if (roleRepository.existsByAuthority(dto.getName())) {
            throw new EntityAlreadyExistsException(ExceptionMessages.ROLE_ALREADY_EXISTS);
        }
        validateRolePattern(dto.getName());
        Role role = modelMapper.map(dto, Role.class);
        role.setId(null);
        role = roleRepository.insert(role);
        return role.toDto();
    }

    @PreAuthorize ("hasRole('ROLE_ADMIN')")
    @Override
    public RoleDto updateRoleById(String id, RoleDto dto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessages.ENTITY_NOT_FOUND));
        if (!role.getId().equals(dto.getId())) {
            throw new DataConflictException(ExceptionMessages.OPERATION_NOT_ALLOWED);
        }
        if (roleRepository.existsByAuthority(dto.getName())) {
            throw new EntityAlreadyExistsException(ExceptionMessages.ROLE_ALREADY_EXISTS);
        }
        validateRolePattern(dto.getName());
        modelMapper.map(dto, role);
        role = roleRepository.save(role);
        return role.toDto();
    }

    @PreAuthorize ("hasRole('ROLE_ADMIN')")
    @Override
    public void deleteRoleById(String id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessages.ENTITY_NOT_FOUND));
        if (userRepository.existsByRole(role.getName())) {
            throw new DataConflictException(ExceptionMessages.ROLE_ALREADY_IN_USE);
        }
        roleRepository.deleteById(id);
    }

    @Override
    public TokenValidationResult validateJwtToken(JwtTokenValidationRequestCommand requestCommand) {
        String accessToken = requestCommand.getAccessToken();
        try {
            DecodedJWT decodedJWT = jwtUtils.verifyToken(accessToken);
            String username = jwtUtils.getUsername(decodedJWT);
            Optional<User> userOptional = userRepository.findByUsername(username);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                boolean valid = user.getIsEnabled() && user.getIsAccountNonLocked()
                        && user.getIsAccountNonExpired() && user.getIsCredentialsNonExpired();
                if (valid) {
                    return TokenValidationResult.builder()
                            .valid(true)
                            .username(user.getUsername())
                            .authorities(user.getAuthorities())
                            .build();
                }
            }
        } catch (JWTVerificationException ignored) {

        }
        return TokenValidationResult.builder()
                .valid(false)
                .build();
    }

    @Override
    public JwtToken renewJwtToken(JwtTokenRenewRequestCommand requestCommand) {
        return null;
    }

    //
    // Helper methods
    //

    private void validateUsernamePattern(String username) {
        final String pattern = "[a-zA-Z0-9._-]+$";
        if (!matchPattern(pattern, username)) {
            throw new IllegalArgumentException(String.format("Username must match pattern \"%s\"", pattern));
        }
    }

    private void validatePasswordPattern(String password) {
        PasswordValidator passwordValidator = new PasswordValidator(Arrays.asList(
                new LengthRule(ConstantValues.PASSWORD_MIN_LENGTH, ConstantValues.PASSWORD_MAX_LENGTH),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()));
        RuleResult ruleResult = passwordValidator.validate(new PasswordData(password));
        if (!ruleResult.isValid()) {
            List<String> messages = passwordValidator.getMessages(ruleResult);
            throw new IllegalArgumentException(messages.toString());
        }
    }

    private void validateRolePattern(String roleName) {
        final String pattern = "^ROLE_[A-Z0-9]+$";
        if (!matchPattern(pattern, roleName)) {
            throw new IllegalArgumentException(String.format("Role role must match pattern \"%s\".", pattern));
        }
    }

    private boolean matchPattern(String pattern, String text) {
        final Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(text);
        return matcher.matches();
    }
}
