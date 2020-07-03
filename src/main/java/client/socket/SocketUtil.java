package client.socket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.Protocol;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-03 10:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocketUtil {

    private final int X = 300;
    private final int Y = 200;

    Protocol protocol;

    public java.net.Socket createSocket(){
        java.net.Socket socket = null;
        try{
            socket = new java.net.Socket();
            SocketAddress socketAddress = new InetSocketAddress(protocol.getTargetIp(),protocol.getDataPort());
            socket.connect(socketAddress,100000);
            socket.setSoTimeout(100000);
        } catch (IOException e) {
            socket = null;
            JDialog jDialog = new JDialog();
            jDialog.setTitle("提示");
            jDialog.add(new JLabel("连接失败！"));
            /**
             * 窗口阻塞
             */
            jDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
            jDialog.setSize(X,Y);
            jDialog.setLocationRelativeTo(null);
            jDialog.setVisible(true);
        }
        return socket;
    }
}
