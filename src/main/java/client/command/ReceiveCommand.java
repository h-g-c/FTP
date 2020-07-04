package client.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author LvHao
 * @Description : 处理客户端命令接受的线程
 * @date 2020-07-03 21:55
 */
public class ReceiveCommand implements Runnable{

    private Socket socket;

    public ReceiveCommand(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader sr = null;
        try{
            sr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while (true) {
                line = sr.readLine();
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
