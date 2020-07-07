package client.gui.action;

import client.command.SendCommand;
import client.gui.ClientFrame;
import client.gui.panel.LocalFilePanel;
import client.util.GetTaskFilePath;
import client.util.IPUtil;
import configuration_and_constant.Constant;
import entity.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import server.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.ServerSocket;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-04 19:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Slf4j(topic = "FileUpload")
public class FileUpload implements ActionListener {

    private JTable jTable;
    private String filePath;
    private String[] fileNames;
    private ServerSocket socketServer;
    private Protocol protocol;
    private String fileType;

    @NonNull
    private LocalFilePanel localFilePanel;

    @NonNull
    private ClientFrame clientFrame;

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            jTable = localFilePanel.getJTable();
            filePath = localFilePanel.getJTextField().getText();
            fileNames = GetTaskFilePath.getUploadName(jTable,filePath);
            fileType = GetTaskFilePath.getDownloadFileType(localFilePanel.getJTable());

            String oneFile = fileNames[0].substring(fileNames[0].lastIndexOf(File.separator)+1,fileNames[0].length());

            protocol = new Protocol();
            socketServer = new ServerSocket(0);
            log.info(socketServer.toString());

            for(int i = 0;i < fileNames.length;i++){
                log.info("请求上传文件名：" + fileNames[i]);
            }
            FileModel fileModel = new FileModel();
            fileModel.setFileName(oneFile);
            fileModel.setFilePath(fileNames[0]);
            if(fileType.equals("BINARY")){
                fileModel.setFileType(FileEnum.BINARY);
                File file = new File(Constant.DEFAULT_PATH + oneFile);
                long size = 0;
                if(file.exists() && file.isFile()){
                    size = file.length();
                }
                fileModel.setFileSize(String.valueOf(size));
            }else if(fileType.equals("TEXT")){
                fileModel.setFileType(FileEnum.TEXT);
            }else{
                fileModel.setFileType(FileEnum.DIR);
            }

            protocol.setData(fileModel);
            protocol.setServiceIp(clientFrame.getJPanel2().getJt1().getText());
            protocol.setCommandPort(Integer.valueOf(clientFrame.getJPanel2().getJt3().getText()));
            protocol.setDataPort(socketServer.getLocalPort());
            protocol.setOperateType(OperateType.DOWNLOAD);
            protocol.setClientIp(IPUtil.getLocalIP());
            protocol.setConnectType(ConnectType.INITIATIVE);
            clientFrame.dataSocket = socketServer;

            SendCommand.sendCommend(protocol,clientFrame.getSocket(),clientFrame.getSocketObjectOutputStream());

        }catch (Exception e1){

        }
    }
}
