package com.kdt.lecture.domain.id2;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Parent2 {

    @EmbeddedId
    private ParentId2 id;
}
