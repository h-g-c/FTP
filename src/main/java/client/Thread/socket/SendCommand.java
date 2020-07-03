package client.Thread.socket;

import java.io.*;
import java.net.Socket;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-03 16:05
 */
public class SendCommand implements Runnable{

    private Socket socket;

    public SendCommand(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectOutputStream os = null;
        //TO DO Something
    }
}
