package ru.learnup.learnup.spring.mvc.homework32.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRoleDto {

    final private Integer id;
    final private String role;
}
