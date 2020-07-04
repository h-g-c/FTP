package client.gui.action;

import client.gui.ClientFrame;
import client.gui.panel.DefaultInfoPanel;
import client.socket.ConnectServer;
import client.util.IPUtil;
import entity.ConnectType;
import entity.OperateType;
import entity.Protocol;
import lombok.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author LvHao
 * @Description : 对连接按钮的事件监听 区分主动模式和被动模式
 * @date 2020-07-03 11:16
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ConnectToServer implements ActionListener {
    private ConnectType type;

    @NonNull
    private JComboBox jComboBox;
    @NonNull
    private DefaultInfoPanel defaultInfoPanel;
    @NonNull
    private ClientFrame clientFrame;

    @SneakyThrows
    @Override
    public void actionPerformed(ActionEvent e) {
        if(jComboBox.getSelectedItem().equals("主动模式")){
            type = ConnectType.INITIATIVE;
        }else{
            type = ConnectType.PASSIVE;
        }
        Protocol protocol = new Protocol();
        protocol.setServiceIp(defaultInfoPanel.getJt1().getText());
        protocol.setCommandPort(Integer.valueOf(defaultInfoPanel.getJt3().getText()));
        protocol.setClientIp(IPUtil.getLocalIP());
        protocol.setOperateType(OperateType.CONNECT);
        protocol.setConnectType(type);
        protocol.setData(null);
        protocol.setDataPort(Integer.valueOf(defaultInfoPanel.getJt3().getText()));

        if(jComboBox.getSelectedItem().equals("主动模式")){
            new ConnectServer(protocol,clientFrame);
            if(clientFrame.getSocket() != null && clientFrame.getSocket().isConnected()){
                jComboBox.setEnabled(false);
                defaultInfoPanel.getJt1().setEditable(false);
                defaultInfoPanel.getJt2().setEditable(false);
                defaultInfoPanel.getJt3().setEditable(false);
                defaultInfoPanel.getJPasswordField().setEditable(false);
                defaultInfoPanel.remove(defaultInfoPanel.getJButton());
                defaultInfoPanel.add(defaultInfoPanel.getJLabel());
                defaultInfoPanel.updateUI();
            }
        }else{
            //TODO something

        }
    }
}
