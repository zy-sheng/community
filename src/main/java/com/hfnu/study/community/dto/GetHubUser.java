package com.hfnu.study.community.dto;

import lombok.Data;

@Data
public class GetHubUser {
    private String name;
    private long id;
    private String bio;
    private String avatar_url;

}
