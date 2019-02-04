package bgs99c.client;

import bgs99c.lab2.Battle;
import bgs99c.lab2.Record;

import java.util.List;

/**
 * Contains info about a single battle.
 */
public class BattleInfo {
    protected List<String> players;
    protected List<String> ranks;
    protected List<Record> results;

    public BattleInfo(List<String> players, List<String> ranks, List<Record> results) {
        this.players = players;
        this.ranks = ranks;
        this.results = results;
    }

    public List<String> getPlayers() {
        return players;
    }

    public List<String> getRanks() {
        return ranks;
    }

    public List<Record> getResults() {
        return results;
    }
}
