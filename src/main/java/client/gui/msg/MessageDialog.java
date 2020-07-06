package client.gui.msg;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.*;

/**
 * @author LvHao
 * @Description : 信息提示对话框
 * @date 2020-07-05 23:54
 */
@Data
@RequiredArgsConstructor
public class MessageDialog {

    private final int X = 300;
    private final int Y = 200;
    private final ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("error.png"));

    private JDialog jDialog;
    private JLabel jLabel;

    @NonNull
    private String title;
    @NonNull
    private String msg;

    public JDialog init(){
        jDialog = new JDialog();
        jDialog.setIconImage(icon.getImage());
        jDialog.setTitle(title);

        jLabel = new JLabel(msg,JLabel.CENTER);
        jLabel.setFont(new Font("宋体",Font.PLAIN,16));
        jDialog.add(jLabel);

        jDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        jDialog.setSize(X,Y);
        jDialog.setLocationRelativeTo(null);
        jDialog.setVisible(true);
        return jDialog;
    }

}
