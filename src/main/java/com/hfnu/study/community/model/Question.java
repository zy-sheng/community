package com.hfnu.study.community.model;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private long gmt_create;
    private long gmt_modified;
    private Integer createor;
    private Integer comment_account;
    private Integer view_account;
    private Integer like_count;
    private String tags;


}
