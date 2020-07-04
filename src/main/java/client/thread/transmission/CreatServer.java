package client.thread.transmission;

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
@Builder
public class CreatServer {
    private Protocol protocolLocal;

    /**
     * 当前命令端口的socket信息
     */
    @NonNull
    private Socket socketServer;

    /**
     * 新建数据传输端口的socket信息
     */
    @NonNull
    private Socket socketLocal;

    /**
     * 服务端相关数据
     */
    @NonNull
    private Protocol protocolServer;

    public Socket creatServer(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(0);
            socketLocal = serverSocket.accept();
            protocolLocal.setServiceIp(protocolServer.getServiceIp());
            protocolLocal.setClientIp(IPUtil.getLocalIP());
            protocolLocal.setOperateType(protocolServer.getOperateType());
            protocolLocal.setData(null);
            protocolLocal.setDataPort(serverSocket.getLocalPort());
            protocolLocal.setCommandPort(null);
            SendCommand.sendCommend(protocolLocal,socketServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socketLocal;
    }
}
