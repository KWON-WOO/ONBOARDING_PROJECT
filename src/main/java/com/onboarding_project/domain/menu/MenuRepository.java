package com.onboarding_project.domain.menu;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findByIdAndIsDeletedFalse(Long id);

    List<Menu> findAllByIsDeletedFalseOrderByIdDesc();
}
