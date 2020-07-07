package client.file;

import client.gui.ClientFrame;
import configuration_and_constant.Constant;
import entity.FileModel;
import util.FileUtil;

import java.io.*;

public class TxtFileReceiveHandle {

    private static File tempFile;
    private static RandomAccessFile randomAccessFile;
    private static int fileLength;
    private static BufferedReader bufferedReader;
    public static void receiveBinaryFile(InputStream inputStream, FileModel fileModel, ClientFrame clientFrame) throws IOException {
       bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
       fileLength=Integer.valueOf(fileModel.getFileSize());
        tempFile=new File(Constant.DEFAULT_PATH + fileModel.getFileName() + ".temp");
        int tempLength= FileUtil.getFileLine(Constant.DEFAULT_PATH + fileModel.getFileName() + ".temp");
        FileWriter fileWriter=new FileWriter(tempFile);
        String value=null;
        while ((value=bufferedReader.readLine())!=null)
        {
            fileWriter.write(value+"\r\n");
            fileWriter.flush();
            tempLength++;
        }
        fileWriter.close();
        bufferedReader.close();
        System.out.println(tempLength);
        System.out.println(fileLength);
        if(tempLength>=fileLength)
        {
            boolean pan=tempFile.renameTo(new File(Constant.DEFAULT_PATH + fileModel.getFileName()));
            System.out.println("重命名"+pan);
        }
    }
}
