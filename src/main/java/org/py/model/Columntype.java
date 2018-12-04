package org.py.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Columntype implements Serializable {
    private static final long serialVersionUID = 7503554552331373741L;
    private Long id;
    private Long pid;
    @NotEmpty(message = "类型名称非可选项")
    private String typename;
    private String col_describe;
}
