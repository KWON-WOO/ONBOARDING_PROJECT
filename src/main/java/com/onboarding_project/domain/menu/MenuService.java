package com.onboarding_project.domain.menu;

import com.onboarding_project.common.exception.CustomException;
import com.onboarding_project.common.exception.ErrorCode;
import com.onboarding_project.domain.menu.dto.MenuCreateRequest;
import com.onboarding_project.domain.menu.dto.MenuResponse;
import com.onboarding_project.domain.menu.dto.MenuUpdateRequest;
import com.onboarding_project.domain.menu.dto.MenuUpdateResponse;
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

    // 메뉴 생성
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

    // 메뉴 단건 조회
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

    // 메뉴 목록 조회
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

    // 메뉴 수정
    @Transactional
    public MenuUpdateResponse updateMenu(String username, Long menuId, MenuUpdateRequest request) {
        Menu menu = findOwnedMenu(username, menuId);

        menu.update(request.getName(), request.getDescription(), request.getPrice());

        menuRepository.saveAndFlush(menu);

        return new MenuUpdateResponse(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.getDescription(),
                menu.getModifiedAt()
        );
    }

    //메뉴 삭제
    @Transactional
    public void deleteMenu(String username, Long menuId) {
        Menu menu = findOwnedMenu(username, menuId);

        menu.deleted();
    }

    private Menu findOwnedMenu(String username, Long menuId) {
        Menu menu = menuRepository.findByIdAndIsDeletedFalse(menuId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_MENU)
        );

        if (!menu.getUser().getUsername().equals(username)) {
            throw new CustomException(ErrorCode.NOT_MENU_OWNER);
        }

        return menu;
    }
}
