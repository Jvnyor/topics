package com.Jvnyor.topics.dtos;

import com.Jvnyor.topics.entities.Topic;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TopicResponse {
    private Long id;

    private String description;

    private LocalDateTime creationDate;

    private LocalDateTime closingDate;

    public static TopicResponse convert(Topic topic) {
        return builder()
                .id(topic.getId())
                .description(topic.getDescription())
                .creationDate(topic.getCreationDate())
                .closingDate(topic.getClosingDate())
                .build();
    }
}
