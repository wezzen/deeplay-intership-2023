package io.deeplay.intership.simons.bot;

import io.deeplay.intership.model.Stone;

import java.util.List;

public class Node {
    boolean isMax;
    Stone[][] field;
    float score;
    List<Node> children;

    public void setMax(boolean max) {
        isMax = max;
    }

    public void setField(Stone[][] field) {
        this.field = field;
    }
}

