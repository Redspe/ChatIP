package client;

import java.io.Serializable;

public class Message implements Serializable{
    private String username;
    private String msg;

    public Message(String username, String msg) {
        this.username = username;
        this.msg = msg;
    }

    public String getUsername() {
        return username;
    }

    public String getMsg() {
        return msg;
    }
}
