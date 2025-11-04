package com.leets.backend.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class SignupRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String pas;

    @NotBlank
    private String nick;

    @NotBlank
    private String name;

    public SignupRequest() {}

    public String getEmail() {return email;}
    public String getPassword() {return pas;}
    public String getNickname() {return nick;}
    public String getName(){return name;}

    public void setEmail(String email) {this.email = email;}
    public void setPassword(String pas) {this.pas = pas;}
    public void setNickname(String nick) {this.nick = nick;}
    public void setName(String name) {this.name = name;}

}
