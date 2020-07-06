package client.command;

import client.gui.ClientFrame;
import client.gui.msg.MessageDialog;
import client.gui.panel.ServerFilePanel;
import client.mode.InitiativeMode;
import client.mode.Mode;
import client.mode.PassiveMode;
import entity.ConnectType;
import entity.Protocol;
import lombok.*;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.net.Socket;

/**
 * @author LvHao
 * @Description :处理客户端命令接受的线程
 * 目前客户端是用监听器来实现多线程逻辑
 * 收发命令在一起
 * 不断监听服务端命令和数据
 * @date 2020-07-04 18:11
 */
@RequiredArgsConstructor
public class ClientCommandHandler implements Runnable {
    private static ServerFilePanel serverFilePanel;
    private static DefaultTableModel model;
    private static Socket socket;
    private static Mode mode;

    @NonNull
    private ClientFrame clientFrame;

    @NonNull
    private ObjectInputStream objectInputStream;

    @Override
    public void run() {
        socket = clientFrame.getSocket();
        serverFilePanel = clientFrame.getJPanel3().getJPanel2();
        model = clientFrame.getJPanel3().getJPanel2().getModel();

        try {
            while (true){
                //读入服务端命令的协议
                Protocol protocolFromSocket = (Protocol) objectInputStream.readObject();
                while(objectInputStream.readObject()!=null) {
                    socket.shutdownInput();
                }
                //如果是主动模式
                if(ConnectType.INITIATIVE.equals(protocolFromSocket.getConnectType())){
                    mode = new InitiativeMode();
                }else {
                    //被动模式
                    mode = new PassiveMode();
                }
                //下面是对各种操作的处理
                switch (protocolFromSocket.getOperateType()){
                    case FILE_PATH:
                    case RETURN_FATHER_DIR:
                    case CONNECT:{
                        mode.showServerDir(protocolFromSocket,serverFilePanel,model);
                    }
                    case PAUSE:{
                        mode.pause();
                    }
                    case DOWNLOAD:{
                        mode.download();
                    }
                    case UPLOAD:{
                        mode.upload();
                    }

                }
            }
        } catch (IOException | ClassNotFoundException e) {
            new MessageDialog("连接状态","连接已断开!").init();
        }
    }
}