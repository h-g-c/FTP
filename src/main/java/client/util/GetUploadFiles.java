package client.util;

import javax.swing.*;

/**
 * @author LvHao
 * @Description : 获取本地将要上传文件的完整路径
 * @date 2020-07-04 22:23
 */
public class GetUploadFiles {

    private static String[] fileNames;

    public static String[] getName(JTable jTable,String filePath){
        int[] rows = jTable.getSelectedRows();
        fileNames = new String[rows.length];
        for(int i = 0; i < rows.length;i ++){
            fileNames[i] = filePath + (String) jTable.getValueAt(rows[i],0);
        }
        return fileNames;
    }
}
