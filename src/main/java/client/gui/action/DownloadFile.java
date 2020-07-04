package client.gui.action;

import client.gui.ClientFrame;
import client.gui.panel.ServerFilePanel;
import client.socket.CreatServer;
import client.util.GetTaskFilePath;
import client.util.IPUtil;
import entity.ConnectType;
import entity.FileModel;
import entity.OperateType;
import entity.Protocol;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.RandomAccessFile;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-04 23:12
 */
@Data
@RequiredArgsConstructor
public class DownloadFile implements ActionListener {

    @NonNull
    private ServerFilePanel serverFilePanel;

    @NonNull
    private ClientFrame clientFrame;

    private JTable jTable;
    private String filePath;
    private String fileName;

    private final String defPath = "D:\\下载";

    @SneakyThrows
    @Override
    public void actionPerformed(ActionEvent e) {
        jTable = serverFilePanel.getJTable();
        filePath = serverFilePanel.getJTextField().getText();
        fileName = GetTaskFilePath.getDownloadName(jTable,filePath);

        File file = new File(defPath+fileName + ".temp");
        RandomAccessFile rad = new RandomAccessFile(defPath + fileName + ".temp", "rw");
        long size = 0;
        if (file.exists() && file.isFile()) {
            size = file.length();
        }

        FileModel fileModel = new FileModel();
        fileModel.setFilePath(fileName);
        fileModel.setFileSize(String.valueOf(size));
        Protocol protocol = new Protocol();
        protocol.setData(fileModel);
        new Thread(new CreatServer(protocol,clientFrame.getSocket())).start();
    }
}
