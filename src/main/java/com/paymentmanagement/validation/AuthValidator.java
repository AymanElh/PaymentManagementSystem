package com.paymentmanagement.validation;

import com.paymentmanagement.exception.ValidationException;

public class AuthValidator {
    public void validateLoginCredentials(String email, String password) {
        validateEmail(email);
        validatePassword(password);
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email is required");
        }

        if (!email.contains("@")) {
            throw new ValidationException("Invalid email format");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new ValidationException("Password is required");
        }

        if (password.length() < 6) {
            throw new ValidationException("Password must be at least 6 characters");
        }
    }
}
