package org.HistoryChecker.model.v4league;

public class MiniSeries {
    private int losses;
    private String progress;
    private int target;
    private int wins;

    public MiniSeries(int losses, String progress, int target, int wins) {
        this.losses = losses;
        this.progress = progress;
        this.target = target;
        this.wins = wins;
    }
}
