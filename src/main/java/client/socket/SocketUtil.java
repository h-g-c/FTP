package client.socket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.Protocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-03 10:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocketUtil {
    Protocol protocol;

    public java.net.Socket createSocket(){
        java.net.Socket socket = null;
        try{
            System.out.println(protocol.getTargetIp());
            System.out.println(protocol.getDataPort());
            socket = new java.net.Socket();
            SocketAddress socketAddress = new InetSocketAddress(protocol.getTargetIp(),protocol.getDataPort());
            socket.connect(socketAddress,100);
            socket.setSoTimeout(50);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }
}
