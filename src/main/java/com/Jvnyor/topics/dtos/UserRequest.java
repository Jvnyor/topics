package com.Jvnyor.topics.dtos;

import lombok.Data;

@Data
public class UserRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String cpf;
    private String vote;
}
