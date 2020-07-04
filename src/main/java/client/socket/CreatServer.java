package client.socket;

import client.command.SendCommand;
import client.util.IPUtil;
import entity.Protocol;
import lombok.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author LvHao
 * @Description :客户端随机打开一个端口接受数据 并将这个端口信息发给服务端 等待服务端连接
 * @date 2020-07-04 10:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class CreatServer implements Runnable{
    private Protocol protocolLocal;

    /**
     * 服务端相关数据
     */
    @NonNull
    private Protocol protocolServer;

    /**
     * 当前命令端口的socket信息
     */
    @NonNull
    private Socket socketLocal;

    /**
     * 新建数据传输端口的socket信息
     */
    private Socket socketServer;

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        while(true){
            try {
                serverSocket = new ServerSocket(0);
                protocolLocal = new Protocol();
                protocolLocal.setServiceIp(protocolServer.getServiceIp());
                protocolLocal.setClientIp(IPUtil.getLocalIP());
                protocolLocal.setTransmissionType(protocolServer.getTransmissionType());
                protocolLocal.setMessage(null);
                protocolLocal.setDataPort(serverSocket.getLocalPort());
                protocolLocal.setCommandPort(null);
                SendCommand.sendCommend(protocolLocal,socketServer);
                socketLocal = serverSocket.accept();

                //TODO accept files

                socketLocal.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(!socketLocal.isClosed()){
                    try{
                        socketLocal.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
