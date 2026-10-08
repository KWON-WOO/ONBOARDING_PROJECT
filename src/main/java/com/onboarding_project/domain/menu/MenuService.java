package com.onboarding_project.domain.menu;

import com.onboarding_project.common.exception.CustomException;
import com.onboarding_project.common.exception.ErrorCode;
import com.onboarding_project.domain.menu.dto.MenuCreateRequest;
import com.onboarding_project.domain.menu.dto.MenuResponse;
import com.onboarding_project.domain.user.User;
import com.onboarding_project.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public MenuResponse createMenu(String username, MenuCreateRequest request) {

        User owner = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER)
        );

        Menu menu = new Menu(owner, request.getName(), request.getDescription(), request.getPrice());
        Menu savedMenu = menuRepository.save(menu);

        return new MenuResponse(
                savedMenu.getId(),
                savedMenu.getName(),
                savedMenu.getPrice(),
                savedMenu.getDescription()
        );
    }

    @Transactional(readOnly = true)
    public MenuResponse getMenu(Long menuId) {
        Menu menu = menuRepository.findByIdAndIsDeletedFalse(menuId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_MENU)
        );
        return new MenuResponse(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.getDescription()
        );
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> getMenuList() {
        return menuRepository.findAllByIsDeletedFalseOrderByIdDesc().stream()
                .map(menu -> new MenuResponse(
                        menu.getId(),
                        menu.getName(),
                        menu.getPrice(),
                        menu.getDescription()
                ))
                .toList();
    }
}
