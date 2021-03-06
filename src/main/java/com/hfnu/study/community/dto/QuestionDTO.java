package com.hfnu.study.community.dto;

import com.hfnu.study.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private long gmtCreate;
    private long gmtModified;
    private Long createor;
    private Integer commentAccount;
    private Integer viewAccount;
    private Integer likeCount;
    private String tags;
    private User user;

}
