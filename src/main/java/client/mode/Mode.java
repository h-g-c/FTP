package client.mode;

import client.gui.panel.ServerFilePanel;
import client.util.DefaultMsg;
import entity.FileModel;
import entity.Protocol;
import lombok.extern.slf4j.Slf4j;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description : 客户端模式和指令处理的抽象类
 * 初步初始化了客户端连接到服务端遍历客户端文件夹的实现
 * 下载 上传 暂停的处理逻辑还未完成
 * 后续的删除考虑是否添加
 * @date 2020-07-05 22:24
 */
@Slf4j(topic = "showServerDir")
public abstract class Mode {

    private final  String[] tableInfo = {"文件名", "大小", "日期"};

    public void showServerDir(Protocol protocolFromSocket, ServerFilePanel serverFilePanel, DefaultTableModel model) {
        if (protocolFromSocket.getData() != null) {
            log.info(protocolFromSocket.getOperateType().toString());
            int i = 0;
            ArrayList<FileModel> fileList = (ArrayList<FileModel>) protocolFromSocket.getData();
            log.info("客户端文件数量：" + fileList.size());
            String[][] data = new String[fileList.size()][3];
            String filepath = null;
            if (fileList.size() == 0) {
                data = null;
                filepath = DefaultMsg.getFilePath();
            } else {
                for (FileModel f : fileList) {
                    data[i][0] = f.getFileName();
                    data[i][1] = f.getFileSize();
                    data[i][2] = f.getChangeTime();
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

    public void upload() {
    }

    public void download() throws IOException {

        //TODO ...
    }

    public void pause() {
    }

}
