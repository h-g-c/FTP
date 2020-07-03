package client.thread.socket;

import util.Protocol;

import java.io.*;
import java.net.Socket;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-03 16:05
 */
public class SendCommand{
    public static void sendCommend(Protocol protocol,Socket socket){
        ObjectOutputStream os = null;
        try{
            if(socket != null && socket.isConnected()){
                os = new ObjectOutputStream(socket.getOutputStream());
                os.writeObject(protocol);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
