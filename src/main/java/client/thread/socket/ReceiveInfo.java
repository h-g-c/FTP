package client.thread.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-03 21:55
 */
public class ReceiveInfo implements Runnable{

    private Socket socket;

    @Override
    public void run() {
        BufferedReader sr = null;
        try{
            sr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String command = null;
            while((command = sr.readLine()) != null){
                //TO DO something
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
