package com.user.user.email;

import lombok.Getter;


//An enum to specify which email template to use
@Getter
public enum EmailTemplateName {

    ACTIVATE_ACCOUNT("activate_account"),
    RESET_PASSWORD("reset_password");

    private final String name;

     EmailTemplateName(String name) {
        this.name = name;
    }
}
