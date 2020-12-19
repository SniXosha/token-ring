package ru.sbt.tokenring.mediums;

import ru.sbt.tokenring.core.Token;
import ru.sbt.tokenring.core.TokenMedium;
import ru.sbt.tokenring.core.TokenMediumBuilder;

import java.util.concurrent.ArrayBlockingQueue;

import static java.lang.Thread.sleep;

public class QueueTokenMedium implements TokenMedium {
    private final int delay;
    private final int capacity;
    private final ArrayBlockingQueue<Token> tokens;

    public QueueTokenMedium(int delay, int capacity) {
        this.delay = delay;
        this.capacity = capacity;
        tokens = new ArrayBlockingQueue<>(capacity);
    }

    public static TokenMediumBuilder queueTokenMediumBuilder(int delay, int capacity) {
        return () -> new QueueTokenMedium(delay, capacity);
    }

    @Override
    public Token getToken() throws InterruptedException {
        sleep(delay);
        return tokens.take();
    }

    @Override
    public void sendToken(Token token) throws InterruptedException {
        sleep(delay);
        tokens.put(token);
    }
}
