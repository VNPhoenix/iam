package vn.dangdnh.service.impl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.modelmapper.ModelMapper;
import org.passay.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import vn.dangdnh.component.JwtUtils;
import vn.dangdnh.definition.ConstantValues;
import vn.dangdnh.definition.CryptoAlgorithm;
import vn.dangdnh.definition.DefaultRole;
import vn.dangdnh.dto.command.RoleCreateCommand;
import vn.dangdnh.dto.request.token.JwtTokenRenewCommand;
import vn.dangdnh.dto.request.token.JwtTokenVerificationCommand;
import vn.dangdnh.dto.request.user.UserSignIn;
import vn.dangdnh.dto.request.user.UserSignUp;
import vn.dangdnh.dto.response.JwtToken;
import vn.dangdnh.dto.response.TokenDetails;
import vn.dangdnh.dto.response.TokenVerification;
import vn.dangdnh.dto.role.RoleDto;
import vn.dangdnh.dto.user.UserInfoDto;
import vn.dangdnh.exception.AuthenticationException;
import vn.dangdnh.exception.EntityExistsException;
import vn.dangdnh.exception.EntityNotFoundException;
import vn.dangdnh.model.role.Role;
import vn.dangdnh.model.user.UserInfo;
import vn.dangdnh.repository.role.RoleRepository;
import vn.dangdnh.repository.user.UserRepository;
import vn.dangdnh.security.encoder.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public IAMServiceImpl(final UserRepository userRepository,
                          final RoleRepository roleRepository,
                          final PasswordEncoder passwordEncoder,
                          final JwtUtils jwtUtils,
                          final ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserInfoDto signUp(UserSignUp request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new EntityExistsException();
        }
        // Validate username
        validateUsernamePattern(request.getUsername());
        // Validate password
        validatePasswordPattern(request.getPassword());
        // Create userInfo details
        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        Date date = new Date();
        UserInfo userInfo = new UserInfo()
                .setUsername(request.getUsername())
                .setPassword(encryptedPassword)
                .setIsEnabled(true)
                .setIsAccountNonExpired(true)
                .setIsAccountNonLocked(true)
                .setIsCredentialsNonExpired(true)
                .setAuthorities(defaultAuthorities())
                .setLastLogin(new Date(0))
                .setUpdatedAt(date)
                .setCreatedAt(date)
                .setCryptoAlgorithm(CryptoAlgorithm.BCRYPT);
        userInfo = userRepository.insert(userInfo);
        return modelMapper.map(userInfo, UserInfoDto.class);
    }

    @Override
    public TokenDetails signIn(UserSignIn request) {
        UserInfo userInfo = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AuthenticationException("Wrong username"));
        if (!passwordEncoder.matches(request.getPassword(), userInfo.getPassword())) {
            throw new AuthenticationException("Wrong password");
        }
        Date date = new Date();
        userRepository.findAndUpdateLastLoginByUsername(userInfo.getUsername(), date);
        String jwtToken = jwtUtils.generateToken(userInfo.getUsername());
        return new TokenDetails()
                .setUsername(userInfo.getUsername())
                .setAccessToken(jwtToken);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public RoleDto findRoleById(String id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(role, RoleDto.class);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public RoleDto createRole(RoleCreateCommand command) {
        if (roleRepository.existsById(command.getRoleName())) {
            throw new EntityExistsException();
        }
        validateRolePattern(command.getRoleName());
        Role role = new Role()
                .setName(command.getRoleName())
                .setDeletable(true);
        role = roleRepository.insert(role);
        return modelMapper.map(role, RoleDto.class);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteRoleById(String id) {
        roleRepository.deleteById(id);
        return "Entity was deleted successfully";
    }

    @Override
    public TokenVerification verifyToken(JwtTokenVerificationCommand requestCommand) {
        String accessToken = requestCommand.getAccessToken();
        try {
            DecodedJWT decodedJWT = jwtUtils.verifyToken(accessToken);
            String username = jwtUtils.getUsername(decodedJWT);
            Optional<UserInfo> userOptional = userRepository.findByUsername(username);
            if (userOptional.isPresent()) {
                UserInfo userInfo = userOptional.get();
                boolean valid = userInfo.getIsEnabled() && userInfo.getIsAccountNonLocked()
                        && userInfo.getIsAccountNonExpired() && userInfo.getIsCredentialsNonExpired();
                if (valid) {
                    return new TokenVerification()
                            .setValid(true)
                            .setUsername(userInfo.getUsername())
                            .setAuthorities(userInfo.getAuthorities());
                }
            }
        } catch (JWTVerificationException ignored) {

        }
        return new TokenVerification()
                .setValid(false);
    }

    @Override
    public JwtToken renewJwtToken(JwtTokenRenewCommand requestCommand) {
        return null;
    }


    //
    // Helper methods
    //

    private List<String> defaultAuthorities() {
        return Collections.singletonList(DefaultRole.ROLE_USER.name());
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
