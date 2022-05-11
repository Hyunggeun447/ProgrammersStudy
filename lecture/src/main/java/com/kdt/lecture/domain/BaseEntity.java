package com.kdt.lecture.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {

    @Column(name = "created_by")
    private String cratedBy;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime cratedAt;
}
