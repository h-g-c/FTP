package client;

import client.gui.ClientFrame;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-02 12:27
 */
public class Test {
    public static void main(String[] args) {
        //测试GUI
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        ClientFrame clientFrame = new ClientFrame();
        clientFrame.setVisible(true);
    }
}
