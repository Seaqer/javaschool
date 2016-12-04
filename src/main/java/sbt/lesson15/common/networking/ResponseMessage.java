package sbt.lesson15.common.networking;

import sbt.lesson15.common.interfaces.Response;
import sbt.lesson15.common.model.Message;

import java.util.ArrayList;
import java.util.List;


public class ResponseMessage implements Response {
    private final CodeMessage CODE;
    private final List<Message> messages;

    public ResponseMessage(CodeMessage CODE, List<Message> messages) {
        this.CODE = CODE;
        this.messages = messages;
    }

    public String getContext() {
        return "";
    }

    public CodeMessage getCode() {
        return CODE;
    }

    public List<Message> getMessages() {
        return new ArrayList<>(messages);
    }
}
