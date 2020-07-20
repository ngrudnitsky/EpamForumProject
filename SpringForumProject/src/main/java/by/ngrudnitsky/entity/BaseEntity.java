package by.ngrudnitsky.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.Instant;

@MappedSuperclass
@Data
abstract class BaseEntity {

    @CreatedDate
    private Instant created;

    @LastModifiedDate
    private Instant updated;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @PrePersist
    void setCreatedAndUpdatedDate() {
        created = Instant.now();
        updated = Instant.now();
    }
}