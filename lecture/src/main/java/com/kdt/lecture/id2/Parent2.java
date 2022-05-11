package com.kdt.lecture.id2;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Parent2 {

    @EmbeddedId
    private String id1;
}
