package lesson15.Common.Interface;

import lesson15.Common.Networking.CodeMessage;
import lesson15.Common.Model.Message;

import java.io.Serializable;
import java.util.List;


public interface Response extends Serializable {
    CodeMessage getCode();

    String getContext();

    List<Message> getMessages();
}
