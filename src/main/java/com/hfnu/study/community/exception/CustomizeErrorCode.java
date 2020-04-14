package com.hfnu.study.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(2001,"你找的问题飘到火星去了"),
    TARGET_NOT_FOUND(2002,"未选中任何问题或者评论进行回复"),
    NO_LOGIN(2003,"当前用户未登录，请登录在试试吧！！！"),
    SYS_ERROR(2004,"操作太快，跟不上你的节奏了！！！"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或者不存在"),
    COMMENT_NOT_FOUND(2006,"你回复的评论飘到火星去了"),
    CONNENT_IS_EMPTY(2007,"你回复的评论为空!!!"),
    READ_NOTIFICATION_FAIL(2008,"不能看别人的信息哦!!!"),
    NOTIFICATIN_NOT_FOUND(2009,"你回复的问题被吃掉了!!!")
    ;



    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
