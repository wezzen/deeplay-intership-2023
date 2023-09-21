package io.deeplay.intership.simons.bot;

import io.deeplay.intership.game.Game;
import io.deeplay.intership.model.Stone;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public int depth;
    public boolean isMax;
    public Stone stone;
    public double score;
    public List<Node> children;

    public Game nodeGame;

    public Node(boolean isMax, int depth) {
        this.isMax = isMax;
        this.children = new ArrayList<>();
        this.depth = depth;
    }

}

