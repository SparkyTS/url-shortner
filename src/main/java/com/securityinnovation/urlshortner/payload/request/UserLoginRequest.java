package com.securityinnovation.urlshortner.payload.request;

import javax.validation.constraints.NotBlank;

/**
 * <h1>UserLoginRequest</h1>
 * <p>
 *   This class will represent login request for user.
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
public class UserLoginRequest {
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}