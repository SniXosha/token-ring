package ru.sbt.tokenring.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class Token {
    private final int id;
    @Setter
    private Integer destinationId = null;
}
