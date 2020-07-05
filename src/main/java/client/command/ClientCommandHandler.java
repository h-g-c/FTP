package client.command;

import client.gui.ClientFrame;
import client.gui.panel.ServerFilePanel;
import client.socket.CreatServer;
import entity.FileModel;
import entity.Protocol;
import lombok.*;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description :处理客户端命令接受的线程
 * 目前客户端是用监听器来实现多线程逻辑
 * 收发命令在一起
 * 此方法留作后续备用
 * @date 2020-07-04 18:11
 */
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class ClientCommandHandler implements Runnable {

    @NonNull
    private ClientFrame clientFrame;


    private final String[] tableInfo = {"文件名", "大小", "日期"};
    private ServerFilePanel serverFilePanel;
    private DefaultTableModel model;

    @Override
    public void run() {
        Socket socket = clientFrame.getSocket();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream()); ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())) {
            while (true) {

                serverFilePanel = clientFrame.getJPanel3().getJPanel2();
                model = clientFrame.getJPanel3().getJPanel2().getModel();
                //读入服务端命令的协议
                Protocol protocolFromSocket = (Protocol) objectInputStream.readObject();

                //如果是主动模式
                if (protocolFromSocket.getData() != null) {
                    int i = 0;
                    ArrayList<FileModel> fileList = (ArrayList<FileModel>) protocolFromSocket.getData();
                    String[][] data = new String[fileList.size()][3];
                    for (FileModel f : fileList) {
                        data[i][0] = f.getFileName();
                        data[i][1] = f.getFileSize();
                        data[i][2] = f.getChangeTime();
                        i++;
                    }
                    i = 0;
                    //接下来判断命令的具体动作
                    //new Thread(new CreatServer(protocolFromSocket, socket)).start();
                    //TODO something
                    model.setRowCount(0);
                    model = new DefaultTableModel(data, tableInfo);
                    serverFilePanel.getJTable().setModel(model);
                    serverFilePanel.getJTextField().setText("Asda");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}