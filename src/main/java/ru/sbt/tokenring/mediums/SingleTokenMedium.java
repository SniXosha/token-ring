package ru.sbt.tokenring.mediums;

import lombok.RequiredArgsConstructor;
import ru.sbt.tokenring.core.Token;
import ru.sbt.tokenring.core.TokenMedium;
import ru.sbt.tokenring.core.TokenMediumBuilder;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

@RequiredArgsConstructor
public class SingleTokenMedium implements TokenMedium {
    private final int delay;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    private volatile Token token = null;

    public static TokenMediumBuilder singleTokenMediumBuilder(int delay) {
        return () -> new SingleTokenMedium(delay);
    }

    @Override
    public Token getToken() throws InterruptedException {
        lock.lock();
        try {
            sleep(delay);
            while (token == null) {
                condition.await();
            }
            Token myToken = token;
            token = null;
            condition.signal();
            return myToken;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void sendToken(Token newToken) throws InterruptedException {
        lock.lock();
        try {
            sleep(delay);
            while (token != null) {
                condition.await();
            }
            token = newToken;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }
}
