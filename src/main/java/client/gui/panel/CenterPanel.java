package client.gui.panel;

import javax.swing.*;
import java.awt.*;

/**
 * @author LvHao
 * @Description :文件面板额的分离  这里又分为了两块
 * @date 2020-07-03 1:59
 */
public class CenterPanel extends JPanel {
    public CenterPanel(){
        init();
    }

    private void init(){

        //设置布局方式
        setLayout(new GridBagLayout());

        GridBagConstraints g1 = new GridBagConstraints();
        g1.gridx = 0;
        g1.gridy = 0;
        g1.weightx = 1;
        g1.weighty = 1;
        g1.fill = GridBagConstraints.BOTH;

        GridBagConstraints g2 = new GridBagConstraints();
        g2.gridx = 1;
        g1.gridy = 0;
        g2.weightx = 1;
        g2.weighty = 1;
        g2.fill = GridBagConstraints.BOTH;

        JPanel jPanel1 = new LocalFilePanel();
        add(jPanel1,g1);
        JPanel jPanel2 = new ServerFilePanel();
        add(jPanel2,g2);
    }
}
