package client.gui.panel;

import client.action.ConnectToServer;
import client.gui.frame.ClientFrame;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-03 1:54
 */
public class DefaultInfoPanel extends JPanel {

    private final String ip = "127.0.0.1";
    private final String name = "root";
    private final String password = "123456";
    private final String port = "20";

    private JButton jButton = new JButton("连接");
    private JLabel jLabel = new JLabel("已连接!");

    public JButton getJButton(){
        return jButton;
    }
    public JLabel getJLabel(){
        return jLabel;
    }

    public DefaultInfoPanel getThis(){
        return this;
    }

    public DefaultInfoPanel(ClientFrame clientFrame){
        init(clientFrame);
    }

    /**
     * 设置初始的默认连接信息
     * 主机：127.0.0.1
     * 用户名：root
     * 密码：123456
     * 端口：20
     */
    private void init(ClientFrame clientFrame){
        JLabel j1 = new JLabel("主机地址:");
        JLabel j2 = new JLabel("用户名：");
        JLabel j3 = new JLabel("密码：");
        JLabel j4 = new JLabel("端口：");
        JComboBox jComboBox = new JComboBox();
        jComboBox.addItem("主动模式");
        jComboBox.addItem("被动模式");

        JTextField jt1 = new JTextField(ip, 16);
        JTextField jt2 = new JTextField(name, 15);
        JTextField jt3 = new JTextField(port, 10);

        JPasswordField jPasswordField = new JPasswordField(password,10);

        setLayout(new FlowLayout());
        add(j1);
        add(jt1);
        add(j2);
        add(jt2);
        add(j3);
        add(jPasswordField);
        add(j4);
        add(jt3);
        add(jComboBox);
        add(jButton);

        jButton.addActionListener(new ConnectToServer(jt1,jt3,jComboBox,this,clientFrame));
    }
}

