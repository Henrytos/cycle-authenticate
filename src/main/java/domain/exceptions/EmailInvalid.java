package domain.exceptions;

public class EmailInvalid extends RuntimeException{

    public EmailInvalid(){
        super("email is invalid");
    }

}
