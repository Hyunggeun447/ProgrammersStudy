package com.kdt.lecture.domain.id2;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ParentId2 implements Serializable {
    private String id1;
    private String id2;
}
