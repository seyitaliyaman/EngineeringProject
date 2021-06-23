package com.safestagram.ws.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    GENERAL_EXCEPTION(1000, "An error has occurred in the system."),
    ARGUMENT_NOT_VALID(1001, "Mandatory parameters cannot be empty"),
    USER_NOT_FOUND(1002,"User not found."),
    UNAUTHORIZED(401,"You must be logged in to be able to operate."),
    USERNAME_EXIST(1003,"This username has already been taken."),
    EMAIL_EXIST(1004,"This email address has already been taken."),
    LOGIN_REQUEST_INCORRECT(1005,"Username or password is incorrect."),
    USER_DELETED(1006,"User is inactive."),
    USER_SESSION_NOT_FOUN(1007,"User session not found."),
    USER_ACCOUNT_NOT_FOUND(1008,"User account not found."),
    USER_ALREADY_LOGIN(1009,"The user is already logged in."),
    USER_PROFILE_NOT_FOUND(1010, "User profile information not found."),
    POST_STORE_FAILED(1011,"There was a problem saving the post."),
    POST_NOT_FOUND(1012,"Post not found.");

    private int code;
    private String message;
}
