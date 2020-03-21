package com.hfnu.study.community.model;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private long gmtCreate;
    private long gmtModified;
    private Integer createor;
    private Integer commentAccount;
    private Integer viewAccount;
    private Integer likeCount;
    private String tags;


}
