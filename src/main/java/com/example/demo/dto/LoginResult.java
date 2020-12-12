package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResult {
    private String success;
    private String accessToken;
    private String email;
    private String region;
    private String phoneNumber;
    private String name;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public LoginResult withSuccess(String success){
        this.success = success;
        return this;
    }

    public LoginResult withAccess(String access){
        this.accessToken = access;
        return this;
    }
}
