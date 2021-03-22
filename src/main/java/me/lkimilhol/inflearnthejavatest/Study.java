package me.lkimilhol.inflearnthejavatest;

import org.yaml.snakeyaml.tokens.AliasToken;

public class Study {

    private StudyStatus status = StudyStatus.DRAFT;
    private int limit;

    public Study() {

    }

    public Study(int limit){
        if (limit < 0) {
            throw new IllegalArgumentException("limit");
        }
        this.limit = limit;
    }

    public StudyStatus getStatus() {
        return status;
    }
}
