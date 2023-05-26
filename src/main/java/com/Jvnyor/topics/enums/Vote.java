package com.Jvnyor.topics.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Vote {
    YES("Yes"), NO("No");

    private final String name;
}
