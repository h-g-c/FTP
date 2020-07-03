package client.action;

import client.gui.frame.ClientFrame;
import client.gui.panel.DefaultInfoPanel;
import client.socket.initiative.ConnectServer;
import client.util.IPUtil;
import lombok.SneakyThrows;
import util.Protocol;
import util.TransmissionType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-03 11:16
 */
public class ConnectToServer implements ActionListener {
    private TransmissionType type;
    private DefaultInfoPanel defaultInfoPanel;
    private JButton jButton;
    private JLabel jLabel;
    private ClientFrame clientFrame;
    private JComboBox jComboBox;
    public ConnectToServer(JComboBox jComboBox,DefaultInfoPanel defaultInfoPanel,ClientFrame clientFrame){
        this.defaultInfoPanel = defaultInfoPanel;
        this.jButton = defaultInfoPanel.getJButton();
        this.jLabel = defaultInfoPanel.getJLabel();
        this.clientFrame = clientFrame;
        this.jComboBox = jComboBox;
    }

    @SneakyThrows
    @Override
    public void actionPerformed(ActionEvent e) {
        if(jComboBox.getSelectedItem().equals("主动模式")){
            type = TransmissionType.INITIATIVE;
        }else{
            type = TransmissionType.PASSIVE;
        }
        Protocol protocol = new Protocol();
        protocol.setTargetIp(defaultInfoPanel.getJt1().getText());
        protocol.setSourceIp(IPUtil.getLocalIP());
        protocol.setTransmissionType(type);
        protocol.setMessage("adssadasd");
        protocol.setDataPort(Integer.valueOf(defaultInfoPanel.getJt3().getText()));
        protocol.setCommandPort(null);

        if(type.name().equals("INITIATIVE")){
            new ConnectServer(protocol,clientFrame);
            if(clientFrame.getSocket() != null && clientFrame.getSocket().isConnected()){
                jComboBox.setEnabled(false);
                defaultInfoPanel.getJt1().setEditable(false);
                defaultInfoPanel.getJt2().setEditable(false);
                defaultInfoPanel.getJt3().setEditable(false);
                defaultInfoPanel.getjPasswordField().setEditable(false);
                defaultInfoPanel.remove(jButton);
                defaultInfoPanel.add(jLabel);
                defaultInfoPanel.updateUI();
            }
        }else{
            //TO DO something

        }
    }
}
