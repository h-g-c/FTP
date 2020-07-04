package server;

import java.io.*;

/**
 * @类名 ExceptFileByByte
 * @描述 用字节数组的形式接受数据
 * @作者 heguicai
 * @创建日期 2020/7/4 下午5:02
 **/
public class ExceptFileByByte {
    DataInputStream das;
    String filePath;
    long point;

    public static void breakPoint(DataInputStream dis, DataOutputStream dos,String fileName) throws IOException {
        long fileLength = dis.readLong();
        File file = new File("/home/heguicai/下载/"+fileName + ".temp");
        RandomAccessFile rad = new RandomAccessFile("/home/heguicai/下载/" + fileName + ".temp", "rw");
        long size = 0;
        if (file.exists() && file.isFile()) {
            size = file.length();
        }
        //从之前的断点的地方进行传输；
        rad.seek(size);
        byte[] value = new byte[1024 * 8];
        while (true) {
            int length = dis.read(value);
            if (length == -1) {
                break;
            }
            rad.write(value);
            size += length;
            if (size == fileLength) {
                break;
            }
        }
        dis.close();
        rad.close();
        //文件重命名
        if (size>=fileLength) {
            file.renameTo(new File("/home/heguicai/下载/" + fileName));
        }
       dos.writeLong(66);
        dos.flush();
        dis.close();
        dos.close();
        rad.close();
    }
}