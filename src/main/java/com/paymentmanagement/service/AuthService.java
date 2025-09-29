package com.paymentmanagement.service;

public interface AuthService {
<<<<<<< Updated upstream
    boolean login(String email, String password);
=======
    LoginSession login(String email, String password) throws Exception;
    LoginSession getCurrentUser();
>>>>>>> Stashed changes
}
