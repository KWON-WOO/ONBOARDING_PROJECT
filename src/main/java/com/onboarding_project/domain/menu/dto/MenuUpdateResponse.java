package com.onboarding_project.domain.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MenuUpdateResponse {
    private final Long id;
    private final String name;
    private final Integer price;
    private final String description;
    private final LocalDateTime modifiedAt;
}
