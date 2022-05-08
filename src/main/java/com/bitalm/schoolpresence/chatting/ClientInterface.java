package com.bitalm.schoolpresence.chatting;

import java.io.IOException;

public interface ClientInterface {
    public void sendMessage(String message,String to) throws IOException;
    public void listenForMessage();
    public void closeEverything();
}
