package com.Jvnyor.topics.dtos;

import com.Jvnyor.topics.entities.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String cpf;
    private String vote;
    private Long topicId;

    public static UserResponse convert(User user) {
        return builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .cpf(user.getCpf())
                .vote(user.getVote().toString())
                .build();
    }
}
