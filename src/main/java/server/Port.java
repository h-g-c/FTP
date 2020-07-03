package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;


/**
 * @类名 Port
 * @描述 用于实现FTP主动模式
 * @作者 heguicai
 * @创建日期 2020/7/2 下午3:26
 **/
public class Port {
    //命令端口
    ServerSocket commandPort;
    private static Logger logger = Logger.getLogger(String.valueOf(Port.class));

    public Port () throws IOException {
        commandPort=new ServerSocket(21);
    }

    public ServerSocket getCommandPort() {
        return commandPort;
    }

    public static void main(String[] args) throws IOException {
        Port service=new Port();
        while (true)
        {
            Socket client=service.getCommandPort().accept();
            logger.info("ip:"+client.getInetAddress()+"连接成功");
            ConnectClient connectClient=new ConnectClient(client);
        }

    }
}