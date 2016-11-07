package lesson15.Common.Networking;

import lesson15.Common.Interface.Response;
import lesson15.Common.Model.Message;

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
