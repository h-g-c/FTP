package client.gui.action;

import client.gui.ClientFrame;
import client.gui.msg.MessageDialog;
import client.gui.panel.ServerFilePanel;
import client.util.GetTaskFilePath;
import configuration_and_constant.Constant;
import entity.FileModel;
import entity.Protocol;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-04 23:12
 */
@Data
@RequiredArgsConstructor
@Slf4j(topic = "DownloadFile")
public class DownloadFile implements ActionListener {

    @NonNull
    private ServerFilePanel serverFilePanel;

    @NonNull
    private ClientFrame clientFrame;

    private JTable jTable;
    private String filePath;
    private String[] fileName;

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            jTable = serverFilePanel.getJTable();
            filePath = serverFilePanel.getJTextField().getText();
            fileName = GetTaskFilePath.getDownloadName(jTable,filePath);

            String oneFile = fileName[0].substring(fileName[0].lastIndexOf(File.separator)+1,fileName[0].length());
            log.info("请求下载文件名：" + oneFile);
            log.info("本地需要建立替代文件名：" + Constant.DEFAULT_PATH + oneFile + ".temp");
            for(int i = 0;i < fileName.length;i++){
                log.info("请求下载文件路径：" + fileName[i]);
            }
        }catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
            new MessageDialog("提示","请先选择文件！").init();
        }
    }
}
