package org.example.network;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NetworkMessage {
    public enum MessageType {
        CONNECT, STATUS_REQUEST, STATUS_RESPONSE, UPDATE
    }

    public enum PlayerAction {
        MOVE_LEFT, MOVE_TOP, MOVE_BOTTOM, MOVE_RIGHT
    }

    @JsonProperty("type")
    private MessageType type;

    @JsonProperty("data")
    private Object data;

    public NetworkMessage(MessageType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public NetworkMessage() {
    }

    public MessageType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
