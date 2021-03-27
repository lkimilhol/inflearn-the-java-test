package me.lkimilhol.inflearnthejavatest.domain;

import me.lkimilhol.inflearnthejavatest.StudyStatus;

public class Study {

    private Member member;

    private StudyStatus status = StudyStatus.DRAFT;
    private int limit;
    private String name;


    public Study() {

    }

    public void setOwner(Member member) {
        this.member = member;
    }

    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Study{" +
                "status=" + status +
                ", limit=" + limit +
                ", name='" + name + '\'' +
                '}';
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

    public int getLimit() {
        return limit;
    }
}
