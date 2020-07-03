package client.util;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LvHao
 * @Description : 获取文件目录 并转换成具体大小
 * @date 2020-07-03 2:02
 */
public class GetFiles {

    private static File[] files = null;
    private static String[][] datas = null;
    /**
     * 获取文件或文件夹的大小
     * 这里用的递归 文件过大速度很慢
     * 需要后续优化
     * @param file 需要计算的文件或文件夹大小
     * @return
     */
    private static long getTotalSizeOfFilesInDir(final File file) {
        if (file.isFile()) {
            return file.length();
        }
        final File[] children = file.listFiles();
        long total = 0;
        if (children != null) {
            for (final File child : children) {
                total += getTotalSizeOfFilesInDir(child);
                if(total>1073741824*3){
                    return -1;
                }
            }
        }
        return total;
    }

    /**
     * 返回文件信息的二维数组
     * @param jComboBox 得到下拉列表是那个磁盘
     * @return
     */
    public static String[][] getFiles(JComboBox jComboBox) {
        File file = (File) jComboBox.getSelectedItem();
        files = file.listFiles();
        datas = new String[files.length][3];
        for(int i = 0; i < files.length; i++){
            datas[i][0] = files[i].getName();
            datas[i][1] = fileSize(getTotalSizeOfFilesInDir(files[i]));
            datas[i][2] = getFileDate(files[i]);
        }
        return datas;
    }

    /**
     * 通过文件路径获取
     * @param fileName
     * @return
     */
    public static String[][] getFiles(String fileName) {
        try{
            File file = new File(fileName);
            File[] files = file.listFiles();
            datas = new String[files.length][3];
            for(int i = 0; i < files.length; i++){
                datas[i][0] = files[i].getName();
                datas[i][1] = fileSize(getTotalSizeOfFilesInDir(files[i]));
                datas[i][2] = getFileDate(files[i]);
            }
            return datas;
        }catch (NullPointerException e){
            System.out.println("该文件为空！");
        }
        return null;
    }
    private static String fileSize(long fileS) {// 转换文件大小
        if(fileS < 0){
            return ">3G";
        }else{
            DecimalFormat df = new DecimalFormat("#.000");
            String fileSizeString = "";
            if (fileS < 1024) {
                fileSizeString = df.format((double) fileS) + "B";
            } else if (fileS < 1048576) {
                fileSizeString = df.format((double) fileS / 1024) + "K";
            } else if (fileS < 1073741824) {
                fileSizeString = df.format((double) fileS / 1048576) + "M";
            } else {
                fileSizeString = df.format((double) fileS / 1073741824) + "G";
            }
            return fileSizeString;
        }
    }

    private static String getFileDate(File file){
        BasicFileAttributes attrs;
        String formatted = "";
        try {
            attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            FileTime time = attrs.creationTime();

            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            formatted = simpleDateFormat.format( new Date( time.toMillis() ) );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formatted;
    }
}
