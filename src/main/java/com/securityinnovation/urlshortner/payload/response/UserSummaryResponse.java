package com.securityinnovation.urlshortner.payload.response;

/**
 * <h1>UserSummaryResponse</h1>
 * <p>
 *   This class will be used to send response for current user detail request.
 * </p>
 *
 * @author Tanay
 * @version 1.0
 */
public class UserSummaryResponse {
    private Long id;
    private String username;
    private String name;
    private String email;

    public UserSummaryResponse(Long id, String username, String name, String email) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}