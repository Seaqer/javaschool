package lesson15.Common.Interface;

import lesson15.Common.Networking.CodeMessage;
import lesson15.Common.Model.Message;

import java.io.Serializable;


public interface Request extends Serializable {

    CodeMessage getCODE();

    Message getMessage();

}
