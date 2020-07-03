package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-03 16:14
 */
public class AcceptTest implements Runnable{

    private Socket socket;

    public AcceptTest(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader br = null;
        PrintWriter pw = null;
        try{
            br = new BufferedReader((new InputStreamReader(socket.getInputStream())));
            pw = new PrintWriter(socket.getOutputStream(),true);
            String s = br.readLine();
            pw.println(s);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
