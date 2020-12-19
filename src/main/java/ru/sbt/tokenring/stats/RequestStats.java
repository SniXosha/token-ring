package ru.sbt.tokenring.stats;

public interface RequestStats {
    void record(long time);

    void save();
}
