package com.stefanini.cycle_authenticate.domain.value_objects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Password {

    private String value;

    public Password() {
    }

    public Password(String value){
        this.value = value;
    }

    public static Boolean isValid(String value){
        String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{6,20}$";
        Pattern PATTERN_PASSWORD = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher =  PATTERN_PASSWORD.matcher(value);
        return matcher.matches();
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
