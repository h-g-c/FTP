package server;

import lombok.*;

import java.io.*;

/**
 * @类名 SendFileByByte
 * @描述 用字节数组的形式发送数据
 * @作者 heguicai
 * @创建日期 2020/7/3 下午10:44
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendFileByByte implements Runnable{
    DataOutputStream das;
    String filePath;
    long point;


    public static boolean breakPoint(DataOutputStream das,String filePath,long point) throws FileNotFoundException {
        System.out.println(filePath);
        File file=new File(filePath);
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        byte[] value;
        long fileLength = file.length();
        System.out.println(fileLength);
        try {
            raf.seek(point);
            value = new byte[(int) (fileLength - point)];
            if (raf.read(value) != (fileLength - point)) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        //每次读取8k
        int sendCont=8*1024;
        int low=0;
        while(true)
        {
            System.out.println(low);
            try {
                if (low + sendCont > fileLength - point) {
                    das.write(value, low, (int) (fileLength - point));
                    das.flush();
                    return true;
                } else {
                    das.write(value, low,low+sendCont);
                    das.flush();
                    low+=sendCont;
                }
                if(low + sendCont == fileLength - point)
                    return true;
            }catch (IOException e)
            {
                e.printStackTrace();
                return false;
            }
        }
    }

    @SneakyThrows
    @Override
    public void run() {
        breakPoint( das, filePath, point);
    }
}