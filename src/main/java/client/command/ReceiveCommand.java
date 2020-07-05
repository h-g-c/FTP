package client.command;

import client.gui.ClientFrame;
import client.gui.panel.ServerFilePanel;
import entity.FileModel;
import entity.Protocol;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-05 8:16
 */
public class ReceiveCommand {

    private final static String[] tableInfo = {"文件名", "大小", "日期"};
    private static ServerFilePanel serverFilePanel;
    private static DefaultTableModel model;
    private static Socket socket;

    public static void receiveCommand(ClientFrame clientFrame, ObjectInputStream objectInputStream){
        socket = clientFrame.getSocket();
        serverFilePanel = clientFrame.getJPanel3().getJPanel2();
        model = clientFrame.getJPanel3().getJPanel2().getModel();
        try {
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
                String filepath = fileList.get(0).getFilePath().replace(fileList.get(0).getFileName(),"");
                serverFilePanel.getJTextField().setText(filepath);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
