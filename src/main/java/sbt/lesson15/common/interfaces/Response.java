package sbt.lesson15.common.interfaces;

import sbt.lesson15.common.networking.CodeMessage;
import sbt.lesson15.common.model.Message;

import java.io.Serializable;
import java.util.List;


public interface Response extends Serializable {
    CodeMessage getCode();

    String getContext();

    List<Message> getMessages();
}
