package com.example.patterns.utils;

import com.example.patterns.vo.auth.TbUsmUserAccessVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.regex.Pattern;

public class ValidationUtils {

    private static final Logger logger = LoggerFactory.getLogger(ValidationUtils.class);

    private static final Pattern UPPERCASE = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE = Pattern.compile("[a-z]");
    private static final Pattern DIGIT = Pattern.compile("[0-9]");
    private static final Pattern SPECIAL_CHAR = Pattern.compile("[^A-Za-z0-9]");
    private static final Pattern COMMON_PASSWORDS = Pattern.compile(
            "(?i)password|123456|123456789|qwerty|abc123|password1|111111|12345678|12345|1234567"
    );

    public static boolean initialValidationPass(TbUsmUserAccessVo tbUsmUserAccessVo) {
        if (tbUsmUserAccessVo == null) {
            return false;
        }

        // Validate that at least one identifier is provided
        boolean isIdentifierProvided = tbUsmUserAccessVo.getUserName() != null ||
                tbUsmUserAccessVo.getEmail() != null ||
                tbUsmUserAccessVo.getPhoneNumber() != null;

        // Checking username is not empty
        if (tbUsmUserAccessVo.getUserName().length() == 0) {
            return false;
        }

        // Checking password is also not empty
        if (tbUsmUserAccessVo.getPassword().length() == 0) {
            return false;
        }

        // Making sure userName is of minimum length 5
        if (tbUsmUserAccessVo.getUserName().length() < 5) {
            logger.error("UserName should be a minimum of length 5");
            throw new IllegalArgumentException("Required field length is too short");
        }

        // Making sure password is strong enough
        String password = tbUsmUserAccessVo.getPassword();
        if (password == null || password.length() < 8 ||
                !UPPERCASE.matcher(password).find() ||
                !LOWERCASE.matcher(password).find() ||
                !DIGIT.matcher(password).find() ||
                !SPECIAL_CHAR.matcher(password).find()) {

            throw new IllegalArgumentException("Password must be at least 8 characters long and include an uppercase character, a lowercase character, a digit, and a special character. Example: Passw0rd!");
        }

        if (COMMON_PASSWORDS.matcher(password).find()) {
            throw new IllegalArgumentException("Password is very common");
        }

        if (!isIdentifierProvided) {
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

    private static boolean isValidEmail(String email) {
        String emailRegex = ApplicationConstants.EMAIL_REGEX;
        return email.matches(emailRegex);
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = ApplicationConstants.PHONE_NUMBER_REGEX;
        return phoneNumber.matches(phoneRegex);
    }
}
