package server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import util.FileUtil;

import java.io.*;

/**
 * @author yinchao
 * @date 2020/7/4 18:50
 */
@AllArgsConstructor
@Slf4j()
@Builder
public class SendFileByLine implements Runnable {
    private String filePath;
    private OutputStream sendOutputStream;
    private int fileLength;

    @SneakyThrows
    @Override
    public void run() {
          sendFile(sendOutputStream,fileLength,filePath);
    }

    public void sendFile(OutputStream sendOutputStream,int fileLength,String filePath) throws IOException {
        int fileLine= FileUtil.getFileLine(filePath);
        File file=new File(filePath);
        FileReader fileReader=new FileReader(file);
        LineNumberReader lineNumberReader=new LineNumberReader(fileReader);
        lineNumberReader.skip(fileLength);
        String str;
        PrintWriter printWriter=new PrintWriter(sendOutputStream);
        while(fileLength<=fileLine)
        {
            str=lineNumberReader.readLine();
            printWriter.println(str);
            fileLength++;
        }
    }
}
