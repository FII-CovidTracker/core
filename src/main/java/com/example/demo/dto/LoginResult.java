package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResult {
    private String accessToken;
    private Long regionId;
    private String name;
    private Long id;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public LoginResult withAccess(String access){
        this.accessToken = access;
        return this;
    }
}
