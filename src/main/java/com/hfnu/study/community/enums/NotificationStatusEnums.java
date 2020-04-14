package com.hfnu.study.community.enums;

public enum NotificationStatusEnums {
    UNREAD(0),READ(1)
    ;
    private int state;

    public int getState() {
        return state;
    }

    NotificationStatusEnums(int state) {
        this.state = state;
    }
}
