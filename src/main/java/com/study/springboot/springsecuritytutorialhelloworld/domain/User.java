package com.study.springboot.springsecuritytutorialhelloworld.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private Integer age;

}
