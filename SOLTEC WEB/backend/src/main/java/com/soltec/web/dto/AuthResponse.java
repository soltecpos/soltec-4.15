package com.soltec.web.dto;

public class AuthResponse {
    private String token;
    private String username;
    private String roleId;

    public AuthResponse(String token, String username, String roleId) {
        this.token = token;
        this.username = username;
        this.roleId = roleId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
