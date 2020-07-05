package client.socket;

import client.command.ClientCommandHandler;
import client.command.SendCommand;
import client.gui.ClientFrame;
import entity.Protocol;

/**
 * @author LvHao
 * @Description : 初始化一个客户端到服务端的连接
 * 发送主动模式 并开始命令接受线程
 * @date 2020-07-03 10:59
 */
public class ConnectServer extends SocketUtil {
    public ConnectServer(Protocol protocol, ClientFrame clientFrame) {
        super(protocol);
        clientFrame.setSocket(this.createSocket());
        clientFrame.setProtocol(protocol);
        SendCommand.sendCommend(protocol,clientFrame.getSocket());
        new Thread(new ClientCommandHandler(clientFrame)).start();
        new Thread(new IsConnect(clientFrame)).start();
    }
}
