package uy.com.club.administration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uy.com.club.administration.domain.user.UserApp;
import uy.com.club.administration.repository.user.UserRepository;
import uy.com.club.administration.security.UserDetailsImpl;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    public static final String USER_NOT_FOUND_WITH_USERNAME = "User Not Found with username: ";
    public static final String USER_NOT_FOUND_WITH_EMAIL = "User Not Found with mail: ";
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userOrMail) throws UsernameNotFoundException {
        UserApp user;
        if (userOrMail.contains("@")) {
            user = userRepository.findByEmail(userOrMail)
                    .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_WITH_EMAIL + userOrMail));
        } else {
            user = userRepository.findByUserName(userOrMail)
                    .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_WITH_USERNAME + userOrMail));
        }
        return UserDetailsImpl.build(user);
    }
}
