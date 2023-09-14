package org.HistoryChecker.model.v5Match;

import java.util.List;

public class Perks {

    PerkStats statPerks;
    List<PerkStyle> styles;

    public Perks(PerkStats statPerks, List<PerkStyle> styles) {
        this.statPerks = statPerks;
        this.styles = styles;
    }

    public PerkStats getStatPerks() {
        return statPerks;
    }

    public void setStatPerks(PerkStats statPerks) {
        this.statPerks = statPerks;
    }

    public List<PerkStyle> getStyles() {
        return styles;
    }

    public void setStyles(List<PerkStyle> styles) {
        this.styles = styles;
    }
}
