package server;

import lombok.SneakyThrows;
import util.Protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * @类名 ConnectClient
 * @描述 用于主动模式连接客户端
 * @作者 heguicai
 * @创建日期 2020/7/2 上午19:32
 **/
public class ConnectClient implements Runnable{
//客户端socket
Socket client;
//数据传输端口
Socket dataPort;
//客户端传输类的通道
ObjectInputStream  ois;
//日志打印
private static Logger logger = Logger.getLogger(String.valueOf(ConnectClient.class));

public ConnectClient(Socket socket) throws IOException {
    client=socket;
     ois=new ObjectInputStream(socket.getInputStream());
}
    @SneakyThrows
    public void run() {
        Protocol commandInf=(Protocol) ois.readObject();
        dataPort=new Socket(commandInf.getTargetIp(),commandInf.getDataPort());

    }
}