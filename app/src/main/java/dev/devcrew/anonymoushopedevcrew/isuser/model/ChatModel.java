package dev.devcrew.anonymoushopedevcrew.isuser.model;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

public class ChatModel {

    String KEY_SENDER_ID,KEY_RECEIVER_ID,KEY_MESSAGE_TEXT,KEY_MESSAGE_TYPE;
    Date KEY_MESSAGE_TIMESTAMP;

    public ChatModel() {
    }

    public String getKEY_SENDER_ID() {
        return KEY_SENDER_ID;
    }

    public void setKEY_SENDER_ID(String KEY_SENDER_ID) {
        this.KEY_SENDER_ID = KEY_SENDER_ID;
    }

    public String getKEY_RECEIVER_ID() {
        return KEY_RECEIVER_ID;
    }

    public void setKEY_RECEIVER_ID(String KEY_RECEIVER_ID) {
        this.KEY_RECEIVER_ID = KEY_RECEIVER_ID;
    }

    public String getKEY_MESSAGE_TEXT() {
        return KEY_MESSAGE_TEXT;
    }

    public void setKEY_MESSAGE_TEXT(String KEY_MESSAGE_TEXT) {
        this.KEY_MESSAGE_TEXT = KEY_MESSAGE_TEXT;
    }

    public String getKEY_MESSAGE_TYPE() {
        return KEY_MESSAGE_TYPE;
    }

    public void setKEY_MESSAGE_TYPE(String KEY_MESSAGE_TYPE) {
        this.KEY_MESSAGE_TYPE = KEY_MESSAGE_TYPE;
    }

    public Date getKEY_MESSAGE_TIMESTAMP() {
        return KEY_MESSAGE_TIMESTAMP;
    }

    public void setKEY_MESSAGE_TIMESTAMP(Date KEY_MESSAGE_TIMESTAMP) {
        this.KEY_MESSAGE_TIMESTAMP = KEY_MESSAGE_TIMESTAMP;
    }
}
