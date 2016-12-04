package sbt.lesson15.common.networking;

import sbt.lesson15.common.interfaces.Response;
import sbt.lesson15.common.model.Message;

import java.util.ArrayList;
import java.util.List;


public class ResponseAnswer implements Response {
    private final CodeMessage CODE;
    private final String contex;

    public ResponseAnswer(CodeMessage CODE, String contex) {
        this.CODE = CODE;
        this.contex = contex;
    }

    public String getContext() {
        return contex;
    }

    public CodeMessage getCode() {
        return CODE;
    }

    public List<Message> getMessages() {
        return new ArrayList<>();
    }
}

