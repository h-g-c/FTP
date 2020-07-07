package client.file;

import client.gui.ClientFrame;
import configuration_and_constant.Constant;
import entity.FileModel;
import lombok.RequiredArgsConstructor;

import java.io.*;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-06 17:00
 */
@RequiredArgsConstructor
public class BinaryFileReceiveHandler {

    private static File tempFile;
    private static RandomAccessFile randomAccessFile;
    private static long fileLength;
    private static DataInputStream dataInputStream;

    public static void receiveBinaryFile(InputStream inputStream, FileModel fileModel, ClientFrame clientFrame) throws IOException {
        try{
            System.out.println(inputStream);
            dataInputStream = new DataInputStream(inputStream);
            fileLength = Long.parseLong(fileModel.getFileSize());
            tempFile = new File(Constant.DEFAULT_PATH + fileModel.getFileName() + ".temp");
            randomAccessFile = new RandomAccessFile(Constant.DEFAULT_PATH + fileModel.getFileName() + ".temp","rw");
            long size = 0;
            if(tempFile.exists() && tempFile.isFile()){
                size = tempFile.length();
            }
            //从之前的断点地方进行接收
            randomAccessFile.seek(size);
            byte[] value = new byte[8];
            while(true){
                int length = dataInputStream.read(value);
                if(length == -1){
                    break;
                }
                randomAccessFile.write(value,0,length);
                size += length;
                if(size >= fileLength){
                    break;
                }
            }
            dataInputStream.close();
            randomAccessFile.close();
            inputStream.close();
            clientFrame.getDataSocket().close();
            //对文件重命名
            System.out.println(size + "以及"+ fileLength);
            if(size >= fileLength){
                System.out.println(Constant.DEFAULT_PATH + fileModel.getFileName());
                boolean pan=tempFile.renameTo(new File(Constant.DEFAULT_PATH + fileModel.getFileName()));
                System.out.println("重命名"+pan);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
