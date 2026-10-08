package com.onboarding_project.domain.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuCreateResponse {
    private final String name;
    private final Integer price;
    private final String description;
}
