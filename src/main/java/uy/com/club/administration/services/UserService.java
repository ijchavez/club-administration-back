package uy.com.club.administration.services;

import uy.com.club.administration.domain.user.UserApp;
import uy.com.club.administration.dto.request.LoginRequest;
import uy.com.club.administration.dto.request.SignupRequest;
import uy.com.club.administration.dto.request.UserDTO;

public interface UserService {
    UserApp registerUser(SignupRequest signUpRequest);
    String login(LoginRequest loginRequest);
    UserDTO getAccount();
    UserDTO getAccountByEmail(String email);
}
