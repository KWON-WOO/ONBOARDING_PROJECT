package com.onboarding_project.domain.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuResponse {
    private final Long id;
    private final String name;
    private final Integer price;
    private final String description;
}
