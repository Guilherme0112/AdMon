package com.br.AdMon.exceptions;

public class EmailExistsException extends Exception{
    public EmailExistsException(String message){
        super(message);
    }

    private static final long serialVersionUID = 1L;
}
