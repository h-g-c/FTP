package client.socket.initiative;

import client.socket.SocketUtil;
import util.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-03 10:59
 */
public class ConnectServer extends SocketUtil {
    public ConnectServer(Protocol protocol){
        super(protocol);
    }

    public void test(){
        java.net.Socket s = null;
        try {
            // 客户机连接服务端的IP地址和端口号
            s = this.createSocket();
            System.out.println("连接到服务器.....");
            // 获得输入流（字节流），再把字节输入流放到缓冲输入流中
            InputStream in = s.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String msg = "";
            msg = br.readLine();
            System.out.println(msg);
            //关闭掉流
            br.close();
            in.close();
            s.close();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
