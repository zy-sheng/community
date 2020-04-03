package com.hfnu.study.community.dto;

import com.hfnu.study.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentor;
    private String content;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeAccount;
    private User user;
}
