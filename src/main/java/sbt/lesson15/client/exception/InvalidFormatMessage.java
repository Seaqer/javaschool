package sbt.lesson15.client.exception;

/**
 * Created by Артём on 06.11.2016.
 */
public class InvalidFormatMessage extends Exception {
    public InvalidFormatMessage(String message){
        super(message);
    }
}
