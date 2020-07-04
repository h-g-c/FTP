package client.util;

import javax.swing.*;

/**
 * @author LvHao
 * @Description : 获取本地将要上传文件的完整路径
 * @date 2020-07-04 22:23
 */
public class GetTaskFilePath {

    private static String[] fileNames;

    public static String[] getUploadName(JTable jTable,String filePath){
        int[] rows = jTable.getSelectedRows();
        fileNames = new String[rows.length];
        for(int i = 0; i < rows.length;i ++){
            fileNames[i] = filePath + (String) jTable.getValueAt(rows[i],0);
        }
        return fileNames;
    }

    public static String getDownloadName(JTable jTable,String filePath){
        int rows = jTable.getSelectedRow();
        return filePath + jTable.getValueAt(rows,0);
    }

    public static String getDownloadFileName(JTable jTable){
        int rows = jTable.getSelectedRow();
        return (String) jTable.getValueAt(rows,0);
    }
}
