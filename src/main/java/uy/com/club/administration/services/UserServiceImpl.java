package uy.com.club.administration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uy.com.club.administration.domain.user.UserApp;
import uy.com.club.administration.dto.request.LoginRequest;
import uy.com.club.administration.dto.request.SignupRequest;
import uy.com.club.administration.dto.request.UserDTO;
import uy.com.club.administration.exception.ErrorConstants;
import uy.com.club.administration.exception.InvalidMailException;
import uy.com.club.administration.exception.UserExistException;
import uy.com.club.administration.repository.user.UserRepository;
import uy.com.club.administration.security.JwtUtils;
import uy.com.club.administration.security.UserDetailsImpl;

import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;
    private final Pattern emailTest = Pattern.compile("^[^@]+@[^@]+\\.[a-zA-Z]{2,}$");

    @Override
    public UserApp registerUser(SignupRequest signUpRequest) {
        validateUserExist(signUpRequest);
        UserApp user = UserApp.builder()
                .userName(createValidUserName(signUpRequest.getEmail()))
                .email(signUpRequest.getEmail())
                .roles(signUpRequest.getRoles())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();
        return userRepository.save(user);
    }

    @Override
    public String login(LoginRequest loginRequest) {
        Authentication authentication;
        if (loginRequest.getEmail() != null) {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } else {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateJwtToken(authentication);


    }

    @Override
    public UserDTO getAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return UserDTO.builder()
                .email(userDetails.getEmail())
                .username(userDetails.getUsername())
                .roles(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .build();
    }

    @Override
    public UserDTO getAccountByEmail(String email) {
        Optional<UserApp> userApp = userRepository.findByEmail(email);
        if (userApp.isPresent()) {
            return UserDTO.builder()
                    .email(userApp.get().getEmail())
                    .username(userApp.get().getUserName())
                    .roles(userApp.get().getRoles().stream().map(Enum::toString).collect(Collectors.toList()))
                    .build();
        }
        throw new UserExistException(ErrorConstants.NOT_EXIST_EMAIL);
    }

    private void validateUserExist(SignupRequest signUpRequest) {
        if (!emailTest.matcher(signUpRequest.getEmail()).matches()) {
            throw new InvalidMailException(ErrorConstants.EMAIL_INVALID);
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserExistException(ErrorConstants.EXIST_EMAIL);
        }
    }

    private String createValidUserName(String email) {
        String userName = email.split("@")[0];
        while (userRepository.existsByUserName(userName)) {
            userName += new Random().nextInt(99);
        }
        return userName;
    }

}
