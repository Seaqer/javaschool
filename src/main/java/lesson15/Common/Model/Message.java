package lesson15.Common.Model;


import java.io.Serializable;

public class Message implements Serializable {
    private final String nickName;
    private final String toUser;
    private final String message;

    public Message(String nickName, String message, String toUser) {
        this.nickName = nickName;
        this.message = message;
        this.toUser = toUser;
    }
    public String getSender(){
        return nickName;
    }

    public String getNickName() {
        return toUser;
    }

    @Override
    public String toString() {
        return nickName
                + " >> "
                + message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (nickName != null ? !nickName.equals(message1.nickName) : message1.nickName != null) return false;
        return message != null ? message.equals(message1.message) : message1.message == null;

    }

    @Override
    public int hashCode() {
        int result = nickName != null ? nickName.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
