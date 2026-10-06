package com.onboarding_project.common;

import jakarta.persistence.Column;

public abstract class BaseSoftDeleteEntity extends BaseEntity {

    @Column(nullable = false)
    private Boolean isDeleted;

    public void deleted() {
        this.isDeleted = true;
    }

    public void recovery() {
        this.isDeleted = false;
    }
}
