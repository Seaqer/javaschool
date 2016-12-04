package sbt.lesson15.common.interfaces;

import sbt.lesson15.common.networking.CodeMessage;
import sbt.lesson15.common.model.Message;

import java.io.Serializable;


public interface Request extends Serializable {

    CodeMessage getCODE();

    Message getMessage();

}
