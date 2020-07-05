package client.gui.action;

import client.command.ReceiveCommand;
import client.command.SendCommand;
import client.gui.ClientFrame;
import client.util.IPUtil;
import entity.OperateType;
import entity.Protocol;
import lombok.AllArgsConstructor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-05 18:10
 */
@AllArgsConstructor
public class GetServerDir implements ActionListener {

    private ClientFrame clientFrame;


    @Override
    public void actionPerformed(ActionEvent e) {
        Protocol protocol = new Protocol();
        protocol.setServiceIp(clientFrame.getProtocol().getServiceIp());
        protocol.setCommandPort(clientFrame.getProtocol().getCommandPort());
        protocol.setClientIp(IPUtil.getLocalIP());
        protocol.setOperateType(OperateType.CONNECT);
        protocol.setConnectType(clientFrame.getProtocol().getConnectType());
        protocol.setOperateType(OperateType.CONNECT);
        protocol.setData(null);
        protocol.setDataPort(clientFrame.getProtocol().getDataPort());

        SendCommand.sendCommend(protocol,clientFrame.getSocket(),clientFrame.getSocketObjectOutputStream());
        ReceiveCommand.receiveCommand(clientFrame,clientFrame.getSocketObjectInputStream());
    }
}
