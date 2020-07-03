package client.socket.initiative;

import client.thread.socket.ReceiveInfo;
import client.thread.socket.SendCommand;
import client.gui.frame.ClientFrame;
import client.socket.SocketUtil;
import util.Protocol;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-03 10:59
 */
public class ConnectServer extends SocketUtil {
    public ConnectServer(Protocol protocol, ClientFrame clientFrame) {
        super(protocol);
        clientFrame.setSocket(this.createSocket());
        SendCommand.sendCommend(protocol,clientFrame.getSocket());
        new Thread(new ReceiveInfo(clientFrame.getSocket())).start();
    }
}
