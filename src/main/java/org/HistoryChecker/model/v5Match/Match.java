package org.HistoryChecker.model.v5Match;

public class Match {
    Metadata metadata;
    Info info;

    public Match(Metadata metadata, Info info){
        this.metadata = metadata;
        this.info = info;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
