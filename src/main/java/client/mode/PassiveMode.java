package client.mode;

import client.gui.ClientFrame;
import client.gui.panel.ServerFilePanel;
import client.util.DefaultMsg;
import entity.FileModel;
import entity.Protocol;
import lombok.extern.slf4j.Slf4j;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description : 被动模式下对服务端命令的处理
 * @date 2020-07-05 22:17
 */
@Slf4j(topic = "PsaaiveModeCli")

public class PassiveMode extends Mode {
    private final  String[] tableInfo = {"文件名", "大小", "日期","文件类型"};

    @Override
    public void showServerDir(Protocol protocolFromSocket, ServerFilePanel serverFilePanel, DefaultTableModel model, ClientFrame clientFrame) {
        log.info(clientFrame.getPsvdataSocket().toString());
        if (clientFrame.getPsvdataSocket().isConnected()) {
            log.info("被动数据端口已连接");
        }
        if (protocolFromSocket.getData() != null) {
            log.info(protocolFromSocket.getOperateType().toString());
            int i = 0;
            ArrayList<FileModel> fileList = (ArrayList<FileModel>) protocolFromSocket.getData();
            log.info("客户端文件数量：" + fileList.size());
            String[][] data = new String[fileList.size()][4];
            String filepath = null;
            if (fileList.size() == 0) {
                data = null;
                filepath = DefaultMsg.getFilePath();
            } else {
                for (FileModel f : fileList) {
                    data[i][0] = f.getFileName();
                    data[i][1] = f.getFileSize();
                    data[i][2] = f.getChangeTime();
                    data[i][3] = String.valueOf(f.getFileType());
                    i++;
                }
                i = 0;
                filepath = fileList.get(0).getFilePath().replace(fileList.get(0).getFileName(), "");
            }
            model.setRowCount(0);
            model = new DefaultTableModel(data, tableInfo);
            serverFilePanel.getJTable().setModel(model);
            log.info("请求文件路径：" + filepath);
            serverFilePanel.getJTextField().setText(filepath);
        }
    }

}
