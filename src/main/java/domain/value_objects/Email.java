package domain.value_objects;

import domain.exceptions.EmailInvalid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {
    private String value;

    public Email(String value){
        if(!this.isValid(value)){
            throw new EmailInvalid();
        }

        this.value = value;
    }

    private Boolean isValid(String email){
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
        Matcher  matcher = EMAIL_PATTERN.matcher(email);

        return matcher.matches();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
