package com.hfnu.study.community.enums;

public enum NotificationTypeEnums {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论");

    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationTypeEnums(int status, String name) {
        this.type = status;
        this.name = name;
    }

    public static String nameOfType(int type){
        for(NotificationTypeEnums notificationTypeEnums : NotificationTypeEnums.values()){
            if(notificationTypeEnums.getType() == type){
                return notificationTypeEnums.getName();
            }
        }
        return "";
    }
}
