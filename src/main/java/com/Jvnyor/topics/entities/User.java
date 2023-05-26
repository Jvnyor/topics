package com.Jvnyor.topics.entities;

import com.Jvnyor.topics.dtos.UserRequest;
import com.Jvnyor.topics.enums.Vote;
import jakarta.persistence.*;
import lombok.*;

@Builder
@ToString
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String cpf;
    @Enumerated(EnumType.STRING)
    private Vote vote;
    @ManyToOne
    private Topic topic;

    public static User convert(UserRequest userRequest) {
        return builder()
                .id(userRequest.getId())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .cpf(userRequest.getCpf())
                .vote(Vote.valueOf(userRequest.getVote().toUpperCase()))
                .build();
    }
}