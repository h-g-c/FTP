package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author yinchao
 * @date 2020/7/3 18:50
 */
public class ServerPassiveMode implements Mode{
    public Socket generateDataTransportSocket(Integer port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket dataTransportSocket = serverSocket.accept();
        return dataTransportSocket;
    }
}
