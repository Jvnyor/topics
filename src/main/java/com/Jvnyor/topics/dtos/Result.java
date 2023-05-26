package com.Jvnyor.topics.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class Result {
    private Long topicId;
    private long yesQuantity;
    private long noQuantity;
}
