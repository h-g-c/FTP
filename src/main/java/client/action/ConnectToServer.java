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
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-03 11:16
 */
public class ConnectToServer implements ActionListener {

    private String ip;
    private int port;
    private TransmissionType type;
    private DefaultInfoPanel defaultInfoPanel;
    private JButton jButton;
    private JLabel jLabel;
    private ClientFrame clientFrame;
    public ConnectToServer(JTextField j1, JTextField j2, JComboBox jComboBox,DefaultInfoPanel defaultInfoPanel,ClientFrame clientFrame){
        this.ip = j1.getText();
        this.port = Integer.parseInt(j2.getText());
        this.defaultInfoPanel = defaultInfoPanel;
        this.jButton = defaultInfoPanel.getJButton();
        this.jLabel = defaultInfoPanel.getJLabel();
        this.clientFrame = clientFrame;

        if(jComboBox.getSelectedItem().equals("主动模式")){
            this.type = TransmissionType.PASSIVE;
        }else{
            this.type = TransmissionType.INITIATIVE;
        }
    }

    @SneakyThrows
    @Override
    public void actionPerformed(ActionEvent e) {
        Protocol protocol = new Protocol();
        protocol.setTargetIp(ip);
        System.out.println(ip);
        protocol.setSourceIp(IPUtil.getLocalIP());
        protocol.setTransmissionType(type);
        protocol.setMessage("adssadasd");
        protocol.setDataPort(port);
        protocol.setCommandPort(null);

        if(type.equals("PASSIVE")){
            ConnectServer connectServer = new ConnectServer(protocol);
            clientFrame.setSocket(connectServer.createSocket());
            Socket socket = clientFrame.getSocket();
            if(socket.isConnected()){
                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                os.writeObject(protocol);
                socket.getOutputStream().flush();
            }
        }else{
            //TO DO something
        }
        if(clientFrame.getSocket().isConnected()){
            defaultInfoPanel.remove(jButton);
            defaultInfoPanel.add(jLabel);
            defaultInfoPanel.updateUI();
        }
    }
}
