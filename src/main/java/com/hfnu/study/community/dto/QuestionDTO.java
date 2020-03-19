package com.hfnu.study.community.dto;

import com.hfnu.study.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
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
    private User user;

}
