package client.gui.frame;

import client.gui.panel.CenterPanel;
import client.gui.panel.DefaultInfoPanel;
import client.gui.panel.TaskPanel;
import client.gui.panel.TopPanel;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-03 1:48
 */
public class ClientFrame extends JFrame {
    public Socket socket = null;
    private final int WEIGHT = 1000;
    private final int HEIGHT = 720;
    private final ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("FTP图标.jpg"));
    /**
     * 构造函数初始化Client窗体
     */
    public ClientFrame(){
        initLClient();
        this.setDefaultCloseOperation(2);
    }

    public void setSocket(Socket socket){
        this.socket = socket;
    }

    public Socket getSocket(){
        return this.socket;
    }

    /**
     * 初始化Client界面
     */
    private void initLClient() {
        //窗体的图标和名称
        setIconImage(icon.getImage());
        setTitle("FTP");
        //窗体大小的设置 居中
        setSize(WEIGHT,HEIGHT);
        setLocationRelativeTo(null);
        //设置背景颜色
        setBackground(Color.WHITE);
        //点击×关闭窗体
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());

        /**
         * 设置顶部面板
         */
        GridBagConstraints g1 = new GridBagConstraints();
        g1.gridx = 0;
        g1.gridy = 0;
        g1.gridwidth = 1;
        g1.gridheight = 1;
        g1.fill = GridBagConstraints.BOTH;
        DefaultInfoPanel jPanel2 = new DefaultInfoPanel(this);
        JPanel jPanel1 = new TopPanel(this,jPanel2);
        add(jPanel1,g1);

        /**
         * 设置默认信息面板
         */
        GridBagConstraints g4 = new GridBagConstraints();
        g4.gridx = 0;
        g4.gridy = 1;
        g1.gridwidth = 1;
        g1.gridheight = 1;
        g4.fill = GridBagConstraints.BOTH;
        add(jPanel2,g4);

        /**
         * 设置本地和远程文件面板
         */
        GridBagConstraints g2 = new GridBagConstraints();
        g2.gridx = 0;
        g2.gridy = 2;
        g2.weighty = 1.7;
        g2.weightx = 1.7;
        g2.fill = GridBagConstraints.BOTH;
        JPanel jPanel3 = new CenterPanel();
        add(jPanel3,g2);

        /**
         * 设置任务队列面板
         */
        GridBagConstraints g3 = new GridBagConstraints();
        g3.gridx = 0;
        g3.gridy = 3;
        g3.weighty = 0.5;
        g3.weightx = 0.5;
        g3.fill = GridBagConstraints.BOTH;
        JTabbedPane jPanel4 = new TaskPanel();
        add(jPanel4,g3);

    }
}