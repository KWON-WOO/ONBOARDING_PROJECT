package com.onboarding_project.domain.menu;

import com.onboarding_project.common.UserRole;
import com.onboarding_project.common.exception.CustomException;
import com.onboarding_project.common.exception.ErrorCode;
import com.onboarding_project.domain.user.User;
import com.onboarding_project.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public MenuCreateResponse createMenu(String username, UserRole role, MenuCreateRequest request) {

        User owner = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER)
        );

        Menu menu = new Menu(owner, request.getName(), request.getDescription(), request.getPrice());
        Menu savedMenu = menuRepository.save(menu);

        return new MenuCreateResponse(
                savedMenu.getName(),
                savedMenu.getPrice(),
                savedMenu.getDescription()
        );
    }
}
