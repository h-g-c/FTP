package client.gui.action;

import client.gui.ClientFrame;
import client.gui.panel.DefaultInfoPanel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
@Data
@RequiredArgsConstructor
public class StopConnect implements ActionListener {
    private final int X = 300;
    private final int Y = 200;

    @NonNull
    private ClientFrame clientFrame;
    @NonNull
    private DefaultInfoPanel defaultInfoPanel;


    @SneakyThrows
    @Override
    public void actionPerformed(ActionEvent e) {
        if((clientFrame.getSocket() != null && !clientFrame.getSocket().isClosed())){
            clientFrame.getSocket().close();
            System.out.println(clientFrame.getSocket().isClosed());
            defaultInfoPanel.getJComboBox().setEnabled(true);
            defaultInfoPanel.getJt1().setEditable(true);
            defaultInfoPanel.getJt2().setEditable(true);
            defaultInfoPanel.getJt3().setEditable(true);
            defaultInfoPanel.getJPasswordField().setEditable(true);
            defaultInfoPanel.remove(defaultInfoPanel.getJLabel());
            defaultInfoPanel.add(defaultInfoPanel.getJButton());
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
