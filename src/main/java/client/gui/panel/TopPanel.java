package client.gui.panel;

import client.action.NewConnector;

import javax.swing.*;
import java.awt.*;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-03 1:50
 */
public class TopPanel extends JPanel {
    public TopPanel(){
        init();
    }

    private void init(){
        /**
         * 设置最上面的那两个按钮
         */
        JButton jButton1 = new JButton("新建连接");
        jButton1.addActionListener(new NewConnector());
        JButton jButton2 = new JButton("断开连接");

        //设置布局方式
        setLayout(new GridBagLayout());

        //新建连接button位置
        GridBagConstraints g1 = new GridBagConstraints();
        g1.gridx = 0;
        g1.gridy = 0;
        g1.weightx = 1;
        g1.weighty = 1;
        g1.fill = GridBagConstraints.BOTH;

        //断开连接button位置
        GridBagConstraints g2 = new GridBagConstraints();
        g2.gridx = 1;
        g2.gridy = 0;
        g2.weightx = 1;
        g2.weighty = 1;
        g2.fill = GridBagConstraints.BOTH;

        add(jButton1,g1);
        add(jButton2,g2);
    }
}
