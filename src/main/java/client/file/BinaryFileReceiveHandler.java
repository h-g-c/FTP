package client.file;

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

    public static void receiveBinaryFile(InputStream inputStream,FileModel fileModel){
        try{
            System.out.println(5555);
            System.out.println(inputStream);
            dataInputStream = new DataInputStream(inputStream);
            fileLength = Long.parseLong(fileModel.getFileSize());
            tempFile = new File(Constant.DEFAULT_PATH + fileModel.getFileName() + ".temp");
            randomAccessFile = new RandomAccessFile(Constant.DEFAULT_PATH + fileModel.getFileName() + ".temp","rw");
            System.out.println(8888888);
            long size = 0;
            if(tempFile.exists() && tempFile.isFile()){
                size = tempFile.length();
                System.out.println(98);
            }
            //从之前的断点地方进行接收
            randomAccessFile.seek(size);
            byte[] value = new byte[1024*8];
            System.out.println(66666);
            while(true){
                System.out.println(77777);
                System.out.println(dataInputStream.readUTF());
                int length = dataInputStream.read(value);
                System.out.println(value);
                System.out.println(444444);
                if(length == -1){
                    break;
                }
                randomAccessFile.write(value);
                size += length;
                if(size == fileLength){
                    break;
                }
            }
            dataInputStream.close();
            randomAccessFile.close();

            System.out.println(6666666);
            //对文件重命名
            if(size >= fileLength){
                tempFile.renameTo(new File(Constant.DEFAULT_PATH + fileModel.getFileName()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
