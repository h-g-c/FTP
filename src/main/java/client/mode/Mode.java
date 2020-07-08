package client.mode;

import client.command.SendCommand;
import client.gui.ClientFrame;
import client.gui.msg.MessageDialog;
import client.gui.panel.ServerFilePanel;
import client.util.DefaultMsg;
import client.util.IPUtil;
import entity.FileModel;
import entity.OperateType;
import entity.Protocol;
import entity.User;
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
 * 完成上传下载
 * 完成删除
 * @date 2020-07-05 22:24
 */
@Slf4j(topic = "showServerDir")
public abstract class Mode {

    private final  String[] tableInfo = {"文件名", "大小", "日期","文件类型"};

    public void showServerDir(Protocol protocolFromSocket, ServerFilePanel serverFilePanel, DefaultTableModel model) {
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

    public void upload(Protocol protocolFromSocket, ClientFrame clientFrame, ArrayList<String[]> data) {
        //上传文件处理
    }

    public void download(Protocol protocolFromSocket, ClientFrame clientFrame, ArrayList<String[]> data){
        //下载文件处理
    }

    public void delete(Protocol protocolFromSocket, ClientFrame clientFrame){
        boolean msg = (boolean) protocolFromSocket.getData();
        if(msg && clientFrame.getSocket() != null &&clientFrame.getSocket().isConnected()){
            new MessageDialog("提示","删除成功",clientFrame).del();

            User user = new User();
            user.setUserName(clientFrame.getJPanel2().getJt2().getText());
            user.setPassword(clientFrame.getJPanel2().getJPasswordField().getText());

            Protocol protocol = new Protocol();
            protocol.setServiceIp(clientFrame.getProtocol().getServiceIp());
            protocol.setCommandPort(clientFrame.getProtocol().getCommandPort());
            protocol.setClientIp(IPUtil.getLocalIP());
            protocol.setOperateType(OperateType.CONNECT);
            protocol.setConnectType(clientFrame.getProtocol().getConnectType());
            protocol.setData(user);
            protocol.setDataPort(clientFrame.getProtocol().getDataPort());

            SendCommand.sendCommend(protocol,clientFrame.getSocket(),clientFrame.getSocketObjectOutputStream());

        }else{
            new MessageDialog("提示","删除失败！",clientFrame).del();
        }
    }

    public void pause(){

    }
}
