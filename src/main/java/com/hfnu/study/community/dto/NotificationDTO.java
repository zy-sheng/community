package com.hfnu.study.community.dto;

import com.hfnu.study.community.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private String notifier;
    private String notifierName;
    private Long outerid;
    private String outerTitle;
    private String typeName;
    private Integer type;
}
