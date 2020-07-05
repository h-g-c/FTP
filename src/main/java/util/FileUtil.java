package util;

import entity.FileEnum;
import entity.FileModel;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * @类名 FileUtil
 * @描述 进行文件的相关操作
 * @作者 heguicai
 * @创建日期 2020/7/3 下午1:37
 **/
public class FileUtil {

    public static ArrayList<FileModel> getFileList(String filePath) {
        ArrayList<FileModel> list = new ArrayList<FileModel>();
        File fatherFile = new File(filePath);
        System.out.println(fatherFile.getAbsolutePath());
        File[] fileList = fatherFile.listFiles();
        for (File kidFile : fileList) {
            FileModel fileCol = FileModel.builder().fileName(kidFile.getName())
                    .filePath(kidFile.getAbsolutePath())
                    .changeTime(getChangeTime(kidFile))
                    .build();
            if (kidFile.isFile()) {
                fileCol.setFileType("0");
                fileCol.setFileSize(String.valueOf(kidFile.length()));
            } else {
                fileCol.setFileType("1");
                fileCol.setFileSize("<DIR>");
            }
            list.add(fileCol);
        }
        return list;
    }

    public static String getChangeTime(File file) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(file.lastModified());
        String time = sdf.format(cal.getTime());
        return time;
    }

//    public static void main(String[] args) {
//        ArrayList<FileModel> list=FileUtil.getFileList("/home");
//        for(FileModel fileModel:list)
//        {
//            System.out.println(fileModel.toString());
//        }
//    }


    /**
     * 用来判断文件类型
     */
    public static FileEnum judgeFileType(String filePath) {
        try {
            String command = "file -i " + filePath;
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String result = br.readLine();
            final String[] s = result.split(" ");
            result = s[1];
            int status = process.waitFor();
            if (status != 0) {
                System.err.println("Failed to call shell's command and the return status's is: " + status);
            }
            System.out.println(result);
            if(result==null){
                return null;
            }
            if(result.startsWith("text")) {
                return FileEnum.TEXT;
            }else if(result.startsWith("inode")){
                throw new IllegalAccessException("文件夹");
            }else{
                return FileEnum.BINARY;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFatherDir(String filePath)
    {
        File file=new File(filePath);
        File fatherFile=file.getParentFile();
        return fatherFile.getName();
    }
}
