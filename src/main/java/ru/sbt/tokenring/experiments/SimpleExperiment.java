package ru.sbt.tokenring.experiments;

import ru.sbt.tokenring.core.NodeRunner;
import ru.sbt.tokenring.stats.RequestStats;
import ru.sbt.tokenring.stats.RequestStatsImpl;
import ru.sbt.tokenring.strategies.SlowFastNodeStrategy;

import static java.lang.Thread.sleep;
import static java.util.List.of;
import static ru.sbt.tokenring.mediums.QueueTokenMedium.queueTokenMediumBuilder;

public class SimpleExperiment {
    public static void main(String[] args) throws InterruptedException {
        RequestStats stats = new RequestStatsImpl("results.txt");
        NodeRunner nodeRunner = new NodeRunner(of(
                new SlowFastNodeStrategy(10, 40, 0.1f, stats),
                new SlowFastNodeStrategy(10, 40, 0.1f, stats),
                new SlowFastNodeStrategy(10, 40, 0.1f, stats),
                new SlowFastNodeStrategy(10, 40, 0.1f, stats),
                new SlowFastNodeStrategy(10, 40, 0.1f, stats),
                new SlowFastNodeStrategy(10, 40, 0.1f, stats),
                new SlowFastNodeStrategy(10, 40, 0.1f, stats),
                new SlowFastNodeStrategy(10, 40, 0.1f, stats),
                new SlowFastNodeStrategy(10, 40, 0.1f, stats),
                new SlowFastNodeStrategy(10, 40, 0.1f, stats),
                new SlowFastNodeStrategy(10, 40, 0.1f, stats),
                new SlowFastNodeStrategy(10, 40, 0.1f, stats),
                new SlowFastNodeStrategy(10, 40, 0.1f, stats),
                new SlowFastNodeStrategy(10, 40, 0.1f, stats),
                new SlowFastNodeStrategy(10, 40, 0.1f, stats),
                new SlowFastNodeStrategy(10, 40, 0.1f, stats)
        ), queueTokenMediumBuilder(5, 1), 16);

        nodeRunner.start();
        sleep(300 * 1000);
        nodeRunner.stop();
        stats.save();
    }
}
