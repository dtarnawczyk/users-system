package org.customers.system.domain.model.audit;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {

    @CreatedBy
    private U createdBy;

    @LastModifiedBy
    private U modifiedBy;

    @CreatedDate
    @Column(name="created_date", nullable = false, updatable = false)
    private LocalDate created;

    @LastModifiedDate
    @Column(name="modified_date")
    private LocalDate modified;

    public LocalDate getCreated() {
        return created;
    }

    public LocalDate getModified() {
        return modified;
    }

    public U getCreatedBy() {
        return createdBy;
    }

    public U getModifiedBy() {
        return modifiedBy;
    }
}
