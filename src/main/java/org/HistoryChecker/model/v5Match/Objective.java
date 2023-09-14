package org.HistoryChecker.model.v5Match;

public class Objective {

    private boolean first;
    private int kills;

    public Objective(boolean first, int kills) {
        this.first = first;
        this.kills = kills;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }
}
