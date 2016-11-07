package lesson15.Common.Networking;

import lesson15.Common.Interface.Response;
import lesson15.Common.Model.Message;

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

