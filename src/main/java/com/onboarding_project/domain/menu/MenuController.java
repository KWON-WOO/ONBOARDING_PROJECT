package com.onboarding_project.domain.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;


}
