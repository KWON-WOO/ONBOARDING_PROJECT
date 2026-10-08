package com.onboarding_project.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseSoftDeleteEntity extends BaseEntity {

    @Column(nullable = false)
    private Boolean isDeleted = false;

    public void deleted() {
        this.isDeleted = true;
    }

    public void recovery() {
        this.isDeleted = false;
    }
}
