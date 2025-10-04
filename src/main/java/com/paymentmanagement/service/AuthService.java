package com.paymentmanagement.service;

import com.paymentmanagement.model.LoginSession;

public interface AuthService {
    LoginSession login(String email, String password) throws Exception;
    LoginSession getCurrentUser();
}
