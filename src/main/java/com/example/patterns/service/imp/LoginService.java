package com.example.patterns.service.imp;

import com.example.patterns.exceptions.UnauthorizedException;
import com.example.patterns.model.ContactDetails;
import com.example.patterns.model.User;
import com.example.patterns.repository.UserRepository;
import com.example.patterns.security.jwt.JwtService;
import com.example.patterns.service.ILoginService;
import com.example.patterns.utils.ApplicationConstants;
import com.example.patterns.vo.auth.TbUsmUserAccessVo;
import com.example.patterns.vo.auth.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class LoginService implements ILoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public CustomUserDetails authenticateUser(TbUsmUserAccessVo tbUsmUserAccessVo) {
        if (!initialValidationPass(tbUsmUserAccessVo)) {
            logger.error("Some fields may be empty, please check");
            throw new IllegalArgumentException("Required fields are missing/invalid");
        }

        Optional<User> userOpt = findUserByIdentifier(tbUsmUserAccessVo);
        CustomUserDetails customUserDetailsResponse = new CustomUserDetails();

        if (userOpt.isPresent()) {
            User currentUser = userOpt.get();
            if (!passwordEncoder.matches(tbUsmUserAccessVo.getPassword(), currentUser.getPassword())) {
                logger.error("Invalid username or password for user: " + currentUser.getUsername());
                throw new UnauthorizedException("Invalid username or password");
            }

            String token = jwtService.generateToken(currentUser.getUsername());
            customUserDetailsResponse.setUser(currentUser);
            customUserDetailsResponse.setToken("Bearer " + token);

        } else {
            User newUser = saveUserDetailsInDB(tbUsmUserAccessVo)   ;
            String token = jwtService.generateToken(newUser.getUsername());

            customUserDetailsResponse = createSuccessResponse(newUser, "NEW_USER", "New User Registered", token);
        }

        return customUserDetailsResponse;
    }

    private Optional<User> findUserByIdentifier(TbUsmUserAccessVo tbUsmUserAccessVo) {
        return userRepository.findByIdentifier(tbUsmUserAccessVo.getUserName())
                .or(() -> userRepository.findByIdentifier(tbUsmUserAccessVo.getEmail()))
                .or(() -> userRepository.findByIdentifier(tbUsmUserAccessVo.getPhoneNumber()));
    }

    private User saveUserDetailsInDB(TbUsmUserAccessVo tbUsmUserAccessVo) {
        logger.info("Saving User Details in DB for signing up");

        User user = new User();
        user.setPassword(passwordEncoder.encode(tbUsmUserAccessVo.getPassword()));
        user.setRoles(tbUsmUserAccessVo.getRoles());
        user.setUsername(tbUsmUserAccessVo.getUserName());

        Set<ContactDetails> contactDetailsSet = new HashSet<>();

        if (tbUsmUserAccessVo.getEmail() != null) {
            contactDetailsSet.add(createContactDetail(user, ApplicationConstants.EMAIL, tbUsmUserAccessVo.getEmail()));
        }

        if (tbUsmUserAccessVo.getPhoneNumber() != null) {
            contactDetailsSet.add(createContactDetail(user, ApplicationConstants.PHONE, tbUsmUserAccessVo.getPhoneNumber()));
        }

        user.setContactDetails(contactDetailsSet);

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            logger.error("Unable to save user to database", e);
            throw new RuntimeException("Unable to save user to database", e);
        }
    }

    private ContactDetails createContactDetail(User user, String type, String value) {
        ContactDetails contactDetails = new ContactDetails();
        contactDetails.setUser(user);
        contactDetails.setType(type);
        contactDetails.setValue(value);
        return contactDetails;
    }

    private CustomUserDetails createErrorResponse(String errorCode, String errorDescription) {
        CustomUserDetails customUserDetailsResponse = new CustomUserDetails();
        customUserDetailsResponse.setErrorCode(errorCode);
        customUserDetailsResponse.setErrorDescription(errorDescription);
        return customUserDetailsResponse;
    }

    private CustomUserDetails createSuccessResponse(User user, String errorCode, String errorDescription, String token) {
        CustomUserDetails customUserDetailsResponse = new CustomUserDetails();
        customUserDetailsResponse.setUser(user);
        customUserDetailsResponse.setErrorCode(errorCode);
        customUserDetailsResponse.setErrorDescription(errorDescription);
        customUserDetailsResponse.setToken("Bearer " + token);
        return customUserDetailsResponse;
    }

    private boolean initialValidationPass(TbUsmUserAccessVo tbUsmUserAccessVo) {
        if (tbUsmUserAccessVo == null) {
            return false;
        }

        // Validate that at least one identifier is provided
        boolean isIdentifierProvided = tbUsmUserAccessVo.getUserName() != null ||
                tbUsmUserAccessVo.getEmail() != null ||
                tbUsmUserAccessVo.getPhoneNumber() != null || tbUsmUserAccessVo.getUserName().length() > 5;

        if (!isIdentifierProvided) {
            return false;
        }

        // Validate the password is not null
        if (tbUsmUserAccessVo.getPassword() == null) {
            return false;
        }

        // Validate email format if provided
        if (tbUsmUserAccessVo.getEmail() != null && !isValidEmail(tbUsmUserAccessVo.getEmail())) {
            return false;
        }

        // Validate phone number format if provided
        if (tbUsmUserAccessVo.getPhoneNumber() != null && !isValidPhoneNumber(tbUsmUserAccessVo.getPhoneNumber())) {
            return false;
        }

        // Validate roles
        Set<String> validRoles = Set.of("MAKER", "CHECKER", "MAKER_CHECKER", "ENQUIRY");
        String[] roles = tbUsmUserAccessVo.getRoles().split(ApplicationConstants.UNDERSCORE_DELIMITER);
        for (String role : roles) {
            if (!validRoles.contains(role.trim())) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = ApplicationConstants.EMAIL_REGEX;
        return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = ApplicationConstants.PHONE_NUMBER_REGEX;
        return phoneNumber.matches(phoneRegex);
    }

}
