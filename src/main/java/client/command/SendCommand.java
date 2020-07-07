package client.command;

import entity.Protocol;

import java.io.*;
import java.net.Socket;

/**
 * @author LvHao
 * @Description : 命令发送的实现
 * @date 2020-07-03 16:05
 */
public class SendCommand{
    public static void sendCommend(Protocol protocol,Socket socket,ObjectOutputStream objectOutputStream){
        try{
            if(socket != null && socket.isConnected()){
                objectOutputStream.writeObject(protocol);
                objectOutputStream.writeObject(null);
                objectOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
