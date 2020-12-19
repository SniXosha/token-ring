package ru.sbt.tokenring.core;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;


@RequiredArgsConstructor
public class Node {
    private final TokenMedium getMedium;
    private final TokenMedium sendMedium;
    private final NodeStrategy strategy;

    private volatile boolean stopped = false;
    private Thread thread = null;

    @SneakyThrows
    public void start(List<Token> initialTokens) {
        for (Token initialToken : initialTokens) {
            sendMedium.sendToken(initialToken);
        }
        thread = new Thread(runnable());
        thread.start();
    }

    public void stop() {
        stopped = true;
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            //interrupted
        }
    }

    private Runnable runnable() {
        return () -> {
            while (!stopped) {
                try {
                    Token token = getMedium.getToken();
                    sendMedium.sendToken(strategy.process(token));
                } catch (InterruptedException e) {
                    break;
                }
            }
        };
    }
}
