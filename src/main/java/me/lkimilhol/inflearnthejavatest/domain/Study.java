package me.lkimilhol.inflearnthejavatest.domain;

import me.lkimilhol.inflearnthejavatest.StudyStatus;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class Study {
    private Member member;
    private StudyStatus status = StudyStatus.DRAFT;
    private int limitCount;
    private String name;

    public LocalDateTime getOpenedDateTime() {
        return openedDateTime;
    }

    private LocalDateTime openedDateTime;

    @ManyToOne
    private Member owner;

    public Study(int limit, String name) {
        this.limitCount = limit;
        this.name = name;
    }

    public Study(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("limit < 0");
        }
        this.limitCount = limit;
    }

    public void setOwner(Member member) {
        this.member = member;
    }

    public void open() {
        this.openedDateTime = LocalDateTime.now();
        this.status = StudyStatus.OPENED;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Study{" +
                "status=" + status +
                ", limit=" + limitCount +
                ", name='" + name + '\'' +
                '}';
    }

    public StudyStatus getStatus() {
        return status;
    }

    public int getLimitCount() {
        return limitCount;
    }
}
