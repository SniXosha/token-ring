package ru.sbt.tokenring.strategies;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.sbt.tokenring.core.NodeStrategy;
import ru.sbt.tokenring.core.Token;
import ru.sbt.tokenring.stats.RequestStats;

import java.util.Random;

import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.sleep;

@RequiredArgsConstructor
public class SimpleNodeStrategy implements NodeStrategy {
    private final int processTime;
    private final RequestStats stats;
    private final Random random = new Random();

    @Setter
    private int id = 0;
    @Setter
    private int numOfNodes = 1;
    private Integer waitingToken = null;
    private long startWaiting = 0;

    @Override
    public Token process(Token token) throws InterruptedException {
        finishWaiting(token);
        processTokenSentForMe(token);
        sendNewToken(token);
        return token;
    }

    private void sendNewToken(Token token) {
        while (token.destinationId() == null) {
            int destination = random.nextInt(numOfNodes);
            if (destination == id) continue;
            token.destinationId(destination);
            waitingToken = token.id();
            startWaiting = currentTimeMillis();
            System.out.println("[" + id + "] Sent token[" + waitingToken + "] to " + destination);
        }
    }

    private void processTokenSentForMe(Token token) throws InterruptedException {
        if (token.destinationId() != null && token.destinationId().equals(id)) {
            System.out.println("[" + id + "] Got token[" + token.id() + "]");
            sleep(processTime);
            System.out.println("[" + id + "] Processed token[" + token.id() + "]");
        }
    }

    private void finishWaiting(Token token) {
        if (waitingToken != null && waitingToken.equals(token.id())) {
            token.destinationId(null);
            waitingToken = null;
            stats.record(currentTimeMillis() - startWaiting);
            System.out.println("[" + id + "] Received token[" + token.id() + "] back");
        }
    }
}
