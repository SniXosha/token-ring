package ru.sbt.tokenring.core;

public interface NodeStrategy {
    void setId(int id);

    void setNumOfNodes(int numOfNodes);

    Token process(Token token) throws InterruptedException;
}
