package org.py.model;

import lombok.*;

import javax.persistence.Id;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Source implements Serializable {
    private static final long serialVersionUID = -7422907553732987261L;
    @Id
    private Integer id;
    private String desc;
}
