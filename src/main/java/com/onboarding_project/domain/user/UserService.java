package com.onboarding_project.domain.user;

import com.onboarding_project.common.CustomException;
import com.onboarding_project.common.JwtUtil;
import com.onboarding_project.common.UserRole;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.onboarding_project.common.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateUserResponse signUp(CreateUserRequest request) {
        String username = request.getUsername();
        String password = passwordEncoder.encode(request.getPassword());
        UserRole userRole = request.getRole();

        // 아이디 중복 시 예외처리
        if (userRepository.existsByUsername(username)) {
            throw new CustomException(EMAIL_ALREADY_EXISTS);
        }

        User user = new User(username, password, userRole);
        userRepository.save(user);

        // 토큰 생성
        String token = jwtUtil.createToken(username, userRole);
        return new CreateUserResponse(token);
    }

    public SignInUserResponse signIn(@Valid SignInUserRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );

        if (passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(MISMATCH_PASSWORD);
        }

        String token = jwtUtil.createToken(user.getUsername(), user.getRole());

        return new SignInUserResponse(token);
    }

}
