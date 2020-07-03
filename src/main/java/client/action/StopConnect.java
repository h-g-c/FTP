package client.action;

import client.gui.frame.ClientFrame;
import client.gui.panel.DefaultInfoPanel;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author LvHao
 * @Description : 关闭连接按钮的相关动作
 * @date 2020-07-03 12:11
 */
public class StopConnect implements ActionListener {
    private final int X = 300;
    private final int Y = 200;
    private DefaultInfoPanel defaultInfoPanel;
    private JButton jButton;
    private JLabel jLabel;
    private ClientFrame clientFrame;
    public StopConnect(ClientFrame clientFrame, DefaultInfoPanel defaultInfoPanel){
        this.defaultInfoPanel = defaultInfoPanel;
        this.jButton = defaultInfoPanel.getJButton();
        this.jLabel = defaultInfoPanel.getJLabel();
        this.clientFrame = clientFrame;
    }


    @SneakyThrows
    @Override
    public void actionPerformed(ActionEvent e) {
        if(clientFrame.getSocket() != null && !clientFrame.getSocket().isClosed()){
            clientFrame.getSocket().close();
            defaultInfoPanel.remove(jLabel);
            defaultInfoPanel.add(jButton);
            defaultInfoPanel.updateUI();
        }else {
            JDialog jDialog = new JDialog();
            jDialog.setTitle("提示");
            jDialog.add(new JLabel("请先连接服务器！"));
            /**
             * 窗口阻塞
             */
            jDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jDialog.setSize(X,Y);
            jDialog.setLocationRelativeTo(null);
            jDialog.setVisible(true);
        }
    }
}
