package server;

import configuration_and_constant.Constant;
import entity.FileModel;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import util.FileUtil;

import java.io.*;

/**
 * @类名 ReceiveFileByLine
 * @描述 用字符的形式接受文件
 * @作者 heguicai
 * @创建日期 2020/7/6 下午5:26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j(topic = "ReceiveFileByLine")
public class ReceiveFileByLine implements Runnable{
InputStream inputStream;
FileModel fileModel;


    /**
     * 字符文件以行的形式接收
     * @param cin
     * @param fileModel
     * @throws IOException
     */
    public void breakoutPoint(InputStream cin,FileModel fileModel) throws IOException {
   BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(cin));
    int fileLength=Integer.valueOf(fileModel.getFileSize());
    File tempFile=new File(Constant.UPLOAD_PATH + fileModel.getFileName() + ".temp");
    FileWriter   fileWriter=new FileWriter(tempFile);
    int tempLength= 0;
    try {
        tempLength = FileUtil.getFileLine(Constant.UPLOAD_PATH + fileModel.getFileName() + ".temp");
        String value=null;
        while ((value=bufferedReader.readLine())!=null)
        {
            fileWriter.write(value+"\r\n");
            fileWriter.flush();
            tempLength++;
        }
        fileWriter.close();
        bufferedReader.close();
        if(tempLength>=fileLength)
        {
            if(tempFile.renameTo(new File(Constant.UPLOAD_PATH + fileModel.getFileName())))
                log.info("文件接收成功");
            else log.info("文件更名失败");

        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    finally {
        fileWriter.close();
        bufferedReader.close();
    }

}


    @SneakyThrows
    @Override
    public void run() {
        breakoutPoint(inputStream,fileModel);
    }
}