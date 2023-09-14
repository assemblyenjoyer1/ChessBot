package org.HistoryChecker.model.v5Match;

import java.util.List;

public class PerkStyle {
    private String description;
    private List<PerkStyleSelection> selections;
    private int style;

    public PerkStyle(String description, List<PerkStyleSelection> selections, int style) {
        this.description = description;
        this.selections = selections;
        this.style = style;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public List<PerkStyleSelection> getSelections() {
        return selections;
    }

    public void setSelections(List<PerkStyleSelection> selections) {
        this.selections = selections;
    }
}