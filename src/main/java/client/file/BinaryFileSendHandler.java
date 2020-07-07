package client.file;

import client.gui.ClientFrame;
import client.util.ArrayListToStringList;
import entity.FileModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-06 17:23
 */
@Slf4j(topic = "BinaryFileReceiveHandler")
@RequiredArgsConstructor
public class BinaryFileSendHandler {

    private static ArrayList<String[]> data = new ArrayList<String[]>();
    private static String[] clo = null;
    private static String[] tableInfo = {"文件名","文件大小","传输状态"};
    private static DefaultTableModel model;

    public static void sendBinaryFile(OutputStream outputStream, FileModel fileModel, ClientFrame clientFrame){
        JTable jTable = clientFrame.getJPanel3().getJPanel1().getJTable();
        clo = new String[3];
        clo[0] = fileModel.getFileName();
        clo[1] = fileModel.getFileSize();
        clo[2] = "";
        data.add(clo);

        if(data.size() > 1){
            data = ArrayListToStringList.flushData(data,clo[0],clo[1]);
        }

        model=new DefaultTableModel(ArrayListToStringList.getData(data), tableInfo);
    }

}
