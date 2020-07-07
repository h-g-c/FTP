package client.file;

import client.gui.ClientFrame;
import client.util.ArrayListToStringList;
import entity.FileModel;
import lombok.Data;
import lombok.NonNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-07 23:56
 */
@Data
public class BinaryFileSendThread implements Runnable{

    private DataOutputStream dataOutputStream;;
    private String[] clo = new String[3];
    private String[] tableInfo = {"文件名","文件大小","传输状态"};
    private DefaultTableModel model;
    private long point;

    @NonNull
    private OutputStream outputStream;
    @NonNull
    private FileModel fileModel;
    @NonNull
    private ClientFrame clientFrame;
    @NonNull
    private JTable jTable;
    @NonNull
    private ArrayList<String[]> data;
    @NonNull
    private int num;


    @Override
    public void run() {
        try{
            dataOutputStream = new DataOutputStream(outputStream);
            point = Long.parseLong(fileModel.getFileSize());
            File file = new File(fileModel.getFilePath());
            RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
            byte[] value = null;
            long fileLength = file.length();
            try{
                randomAccessFile.seek(point);
                value = new byte[(int)(fileLength - point)];
                if(randomAccessFile.read(value) != (fileLength - point)){
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //每次读取的数量大小
            int sendCont = 8*1024;
            int low = 0;
            long size = 0;
            jTable.setValueAt(low,num,2);
            while(clientFrame.getDataSocket() != null) {
                try {
                    if (low + sendCont >= fileLength - point) {
                        dataOutputStream.write(value, low, (int) (fileLength - point -low));
                        jTable.setValueAt(value.length,num,2);
                        size+=value.length;
                        dataOutputStream.flush();
                        dataOutputStream.close();
                        randomAccessFile.close();
                        outputStream.flush();
                        break;
                    } else {
                        dataOutputStream.write(value, low,sendCont);
                        jTable.setValueAt(value.length,num,2);
                        dataOutputStream.flush();
                        low+=sendCont;
                        size+=low;
                    }
                }catch (IOException e)
                {
                    e.printStackTrace();
                    break;
                }
            }
            if(clientFrame.getDataSocket() != null){
                clientFrame.getDataSocket().close();
                clientFrame.setDataSocket(null);
            }
            model = new DefaultTableModel(ArrayListToStringList.flushData(data,fileModel.getFileName(),fileModel.getFileSize(), (long) size),tableInfo);
            jTable.setModel(model);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}