package server;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @类名 SendFileByByte
 * @描述 用字节数组的形式进行数据的传输
 * @作者 heguicai
 * @创建日期 2020/7/3 下午10:44
 **/
public class SendFileByByte {

    public static boolean sendFile(DataOutputStream das,String filePath) throws IOException {
         return  breakPoint(das,filePath,0);
    }

    public static boolean breakPoint(DataOutputStream das,String filePath,long point) throws IOException {
        File file=new File(filePath);
        FileInputStream fileInputStream=new FileInputStream(file);
        long fileLength=file.length();
        das.writeLong(fileLength);
        das.flush();
        System.out.println("======== 开始传输文件 ========");
        byte[] bytes = new byte[1];
        int length = 0;
        long sendTotal=point;
        fileInputStream.skip(point);
        while ((length = fileInputStream.read(bytes, 0, bytes.length)) != -1) {
            das.write(bytes, 0, length);
            das.flush();
            sendTotal+=length;
        }
        System.out.println("======== 文件传输成功 ========");
        if (sendTotal==fileLength)
            return true;
        else return false;
    }
}