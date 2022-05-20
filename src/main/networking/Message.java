package main.networking;

import java.io.Serializable;

public class Message implements Serializable {
    public enum MessageType {
        joinRequest,
        joinAccepted,
        joinDeclined,
        lobbyUpdate,
        serverClosing,
        serverStartingBattlefieldBuild
    }

    public Object data;
    public MessageType type;

    Message(MessageType type, Object data) {
        this.type = type;
        this.data = data;
    }
}
