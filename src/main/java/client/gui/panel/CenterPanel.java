package client.gui.panel;

import client.gui.MyGridBagConstraints;

import javax.swing.*;
import java.awt.*;

/**
 * @author LvHao
 * @Description :文件面板的分离  这里又分为了两块
 * @date 2020-07-03 1:59
 */
public class CenterPanel extends JPanel {
    public CenterPanel(){
        init();
    }

    private void init(){

        //设置布局方式
        setLayout(new GridBagLayout());

        JPanel jPanel1 = new LocalFilePanel();
        add(jPanel1,new MyGridBagConstraints(0,0,1,1).init2());
        JPanel jPanel2 = new ServerFilePanel();
        add(jPanel2,new MyGridBagConstraints(1,0,1,1).init2());
    }
}
