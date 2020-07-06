package client.gui.action;

import client.command.ReceiveCommand;
import client.command.SendCommand;
import client.gui.ClientFrame;
import client.gui.msg.MessageDialog;
import client.gui.panel.ServerFilePanel;
import client.util.IPUtil;
import entity.ConnectType;
import entity.FileModel;
import entity.OperateType;
import entity.Protocol;
import lombok.AllArgsConstructor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-05 18:19
 */
@AllArgsConstructor
public class ReturnServerDir implements ActionListener {

    private ClientFrame clientFrame;

    private ServerFilePanel serverFilePanel;

    @Override
    public void actionPerformed(ActionEvent e) {
        if(clientFrame.getSocket() != null && clientFrame.getSocket().isConnected()){
            FileModel fileModel = new FileModel();

            String fileName = null;
            Protocol protocol = new Protocol();

            //判断目录下是否有文件
            if(serverFilePanel.getJTable().getRowCount() != 0){
                fileName = serverFilePanel.getJTextField().getText()+serverFilePanel.getJTable().getValueAt(0,0).toString();
                protocol.setOperateType(OperateType.RETURN_FATHER_DIR);
                fileModel.setFilePath(fileName);
                protocol.setData(fileModel);
            }else{
                fileName = serverFilePanel.getJTextField().getText().substring(0,serverFilePanel.getJTextField().getText().length()-1);
                fileName = fileName.substring(0,fileName.lastIndexOf(File.separator));
                protocol.setOperateType(OperateType.FILE_PATH);
                protocol.setData(fileName);
            }

            protocol.setServiceIp(clientFrame.getProtocol().getServiceIp());
            protocol.setCommandPort(clientFrame.getProtocol().getCommandPort());
            protocol.setClientIp(IPUtil.getLocalIP());
            protocol.setConnectType(ConnectType.INITIATIVE);
            protocol.setDataPort(clientFrame.getProtocol().getDataPort());


            SendCommand.sendCommend(protocol,clientFrame.getSocket(),clientFrame.getSocketObjectOutputStream());
        }else{
            new MessageDialog("提示","请先连接客户端").init();
        }
    }
}
