package org.HistoryChecker.model.v5Match;

public class Ban {

    int championId;
    int pickTurn;


    public Ban(int championId, int pickTurn) {
        this.championId = championId;
        this.pickTurn = pickTurn;
    }

    public int getChampionId() {
        return championId;
    }

    public int getPickTurn() {
        return pickTurn;
    }
}
