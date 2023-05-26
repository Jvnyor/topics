package com.Jvnyor.topics.entities;

import com.Jvnyor.topics.dtos.TopicRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@ToString
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "tb_topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private LocalDateTime creationDate;

    private LocalDateTime closingDate;

    public static Topic convert(TopicRequest topicRequest) {
        return builder()
                .id(null)
                .description(topicRequest.getDescription())
                .creationDate(LocalDateTime.now())
                .closingDate(null)
                .build();
    }
}
