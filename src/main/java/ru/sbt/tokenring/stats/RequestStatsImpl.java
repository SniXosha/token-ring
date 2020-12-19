package ru.sbt.tokenring.stats;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.currentTimeMillis;
import static java.nio.file.Files.writeString;
import static java.nio.file.Path.of;
import static java.util.stream.Collectors.joining;

@RequiredArgsConstructor
public class RequestStatsImpl implements RequestStats {
    private final String destination;

    private final List<Long> times = new ArrayList<>();
    private final List<Long> timestamps = new ArrayList<>();

    @Override
    public void record(long time) {
        times.add(time);
        timestamps.add(currentTimeMillis());
    }

    @SneakyThrows
    @Override
    public void save() {
        String result = times.stream()
                .map(String::valueOf)
                .collect(joining(" ")) + "\n" +
                timestamps.stream()
                        .map(String::valueOf)
                        .collect(joining(" "));
        writeString(of(destination), result);
    }
}
