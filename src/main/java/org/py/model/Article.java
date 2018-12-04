package org.py.model;

import lombok.*;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Article implements Serializable {
    private static final long serialVersionUID = 7278398737046764157L;
    @Id
    private Long id;
    @NotEmpty(message = "必须填写标题")
    private String title;
    @NotEmpty(message = "必须填写作者")
    private String author;
    @NotEmpty(message = "必须填写来源")
    private String source;
    private LocalDateTime publishdatetime;
    private String shorttitle;
    private Boolean isvarify;
    @NotEmpty(message = "必须填写所属栏目")
    private String columntype;
    private String content;
}
