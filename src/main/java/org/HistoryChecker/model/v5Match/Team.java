package org.HistoryChecker.model.v5Match;

import java.util.List;

public class Team {

    List<Ban> bans;
    Objectives objectives;
    int teamId;
    boolean win;


    public Team(List<Ban> bans, Objectives objectives, int teamId, boolean win) {
        this.bans = bans;
        this.objectives = objectives;
        this.teamId = teamId;
        this.win = win;
    }

    public List<Ban> getBans() {
        return bans;
    }

    public Objectives getObjectives() {
        return objectives;
    }

    public int getTeamId() {
        return teamId;
    }

    public boolean isWin() {
        return win;
    }
}
