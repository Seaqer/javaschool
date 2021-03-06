package sbt.lesson15.common.networking;

import sbt.lesson15.common.interfaces.Request;
import sbt.lesson15.common.model.Message;

/**
 * Created by Артём on 06.11.2016.
 */
public class RequestImpl implements Request {
    private final CodeMessage CODE;
    private final Message message;

    public RequestImpl(CodeMessage CODE, Message message) {
        this.CODE = CODE;
        this.message = message;
    }

    public CodeMessage getCODE() {
        return CODE;
    }

    public Message getMessage() {
        return message;
    }
}
