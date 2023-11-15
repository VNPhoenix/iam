package vn.dangdnh.service.impl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.modelmapper.ModelMapper;
import org.passay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import vn.dangdnh.definition.ConstantValues;
import vn.dangdnh.definition.CryptoAlgorithm;
import vn.dangdnh.definition.DefaultRole;
import vn.dangdnh.definition.message.exception.ExceptionMessages;
import vn.dangdnh.dto.command.RoleCreateCommand;
import vn.dangdnh.dto.request.token.JwtTokenRenew;
import vn.dangdnh.dto.request.token.JwtTokenVerification;
import vn.dangdnh.dto.request.user.UserSignIn;
import vn.dangdnh.dto.request.user.UserSignUp;
import vn.dangdnh.dto.response.JwtToken;
import vn.dangdnh.dto.response.TokenDetails;
import vn.dangdnh.dto.response.TokenVerification;
import vn.dangdnh.dto.role.RoleDto;
import vn.dangdnh.dto.user.UserInfoDto;
import vn.dangdnh.exception.AuthenticationException;
import vn.dangdnh.exception.EntityAlreadyExistsException;
import vn.dangdnh.exception.EntityNotFoundException;
import vn.dangdnh.model.role.Role;
import vn.dangdnh.model.user.UserInfo;
import vn.dangdnh.repository.role.RoleRepository;
import vn.dangdnh.repository.user.UserRepository;
import vn.dangdnh.component.JwtUtils;
import vn.dangdnh.service.RoleService;
import vn.dangdnh.service.TokenService;
import vn.dangdnh.service.UserService;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IAMServiceImpl implements UserService, TokenService, RoleService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public IAMServiceImpl(final UserRepository userRepository,
                          final RoleRepository roleRepository,
                          final JwtUtils jwtUtils,
                          final ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserInfoDto signUp(UserSignUp request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new EntityAlreadyExistsException(ExceptionMessages.USERNAME_ALREADY_EXISTS);
        }
        // Validate username
        validateUsernamePattern(request.getUsername());
        // Validate password
        validatePasswordPattern(request.getPassword());
        // Create userInfo details
        String salt = BCrypt.gensalt();
        String encryptedPassword = BCrypt.hashpw(request.getPassword(), salt);
        Date date = new Date();
        UserInfo userInfo = new UserInfo();
        userInfo = userInfo.username(request.getUsername())
                .password(encryptedPassword)
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .authorities(defaultAuthorities())
                .lastLogin(new Date(0))
                .updatedAt(date)
                .createdAt(date)
                .cryptoAlgorithm(CryptoAlgorithm.BCRYPT);
        userInfo = userRepository.insert(userInfo);
        return mapToDto(userInfo);
    }

    @Override
    public TokenDetails signIn(UserSignIn request) {
        UserInfo userInfo = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AuthenticationException("Wrong username"));
        if (!BCrypt.checkpw(request.getPassword(), userInfo.password())) {
            throw new AuthenticationException("Wrong password");
        }
        Date date = new Date();
        userRepository.findAndUpdateLastLoginByUsername(userInfo.username(), date);
        String jwtToken = jwtUtils.generateToken(userInfo.username());
        return new TokenDetails()
                .setUsername(userInfo.username())
                .setAccessToken(jwtToken);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public RoleDto findRoleById(String id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return mapToDto(role);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public RoleDto createRole(RoleCreateCommand command) {
        if (roleRepository.existsById(command.getRoleName())) {
            throw new EntityAlreadyExistsException();
        }
        validateRolePattern(command.getRoleName());
        Role role = new Role();
        role.setName(command.getRoleName());
        role = roleRepository.insert(role);
        return mapToDto(role);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRoleById(String id) {
        roleRepository.deleteById(id);
    }

    @Override
    public TokenVerification verifyToken(JwtTokenVerification requestCommand) {
        String accessToken = requestCommand.getAccessToken();
        try {
            DecodedJWT decodedJWT = jwtUtils.verifyToken(accessToken);
            String username = jwtUtils.getUsername(decodedJWT);
            Optional<UserInfo> userOptional = userRepository.findByUsername(username);
            if (userOptional.isPresent()) {
                UserInfo userInfo = userOptional.get();
                boolean valid = userInfo.isEnabled() && userInfo.isAccountNonLocked()
                        && userInfo.isAccountNonExpired() && userInfo.isCredentialsNonExpired();
                if (valid) {
                    return new TokenVerification()
                            .setValid(true)
                            .setUsername(userInfo.username())
                            .setAuthorities(userInfo.authorities());
                }
            }
        } catch (JWTVerificationException ignored) {

        }
        return new TokenVerification()
                .setValid(false);
    }

    @Override
    public JwtToken renewJwtToken(JwtTokenRenew requestCommand) {
        return null;
    }


    //
    // Helper methods
    //

    private List<String> defaultAuthorities() {
        return Collections.singletonList(DefaultRole.ROLE_USER.name());
    }

    private UserInfoDto mapToDto(UserInfo o) {
        return o != null ? modelMapper.map(o, UserInfoDto.class) : null;
    }

    private RoleDto mapToDto(Role o) {
        return o != null ? modelMapper.map(o, RoleDto.class) : null;
    }

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
