package ru.sbt.tokenring.core;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

@RequiredArgsConstructor
public class NodeRunner {
    private final List<NodeStrategy> strategies;
    private final TokenMediumBuilder tokenMediumBuilder;
    private final int numOfTokens;

    private List<Node> nodes = null;

    public void start() {
        setupNodes();
        setupStrategies();
        startNodes(generateInitialTokens());
    }

    public void stop() {
        range(0, nodes.size()).forEach(i -> nodes.get(i).stop());
    }

    private void startNodes(List<List<Token>> initialTokens) {
        range(0, nodes.size())
                .forEach(i -> nodes.get(i).start(initialTokens.get(i)));
    }

    private void setupNodes() {
        List<TokenMedium> mediums = range(0, strategies.size())
                .mapToObj(i -> tokenMediumBuilder.createTokenMedium())
                .collect(toList());
        nodes = range(0, mediums.size())
                .mapToObj(i -> new Node(mediums.get(i), mediums.get((i + 1) % mediums.size()), strategies.get(i)))
                .collect(toList());
    }

    private void setupStrategies() {
        range(0, nodes.size()).forEach(i -> {
            strategies.get(i).setId(i);
            strategies.get(i).setNumOfNodes(strategies.size());
        });
    }

    private List<List<Token>> generateInitialTokens() {
        List<List<Token>> initialTokens = range(0, strategies.size())
                .mapToObj(i -> new ArrayList<Token>())
                .collect(toList());
        range(0, numOfTokens).forEach(i -> initialTokens.get(i % initialTokens.size()).add(new Token(i)));
        return initialTokens;
    }
}
