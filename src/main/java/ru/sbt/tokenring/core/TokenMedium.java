package ru.sbt.tokenring.core;

public interface TokenMedium {
    Token getToken() throws InterruptedException;

    void sendToken(Token token) throws InterruptedException;
}
