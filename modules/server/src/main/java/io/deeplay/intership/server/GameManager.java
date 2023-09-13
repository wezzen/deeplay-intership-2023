package io.deeplay.intership.server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GameManager {
    public final ConcurrentMap<String, ServerGame> map = new ConcurrentHashMap<>();

}
