package entity;

import configuration_and_constant.ThreadPool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.Port;
import server.SendFileByByte;
import server.SendFileByLine;
import util.FileUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yinchao
 * @date 2020/7/3 18:54
 */
@Data
public abstract class Mode {
    Socket dataSocket;
    Socket commandSocket;

    abstract void initialization(ObjectOutputStream objectOutputStream, Protocol protocolFromSocket) throws IOException;

    abstract void getDataPort();

    void upload() {
    }

    void download(Protocol protocolFromSocket,String filePath,ObjectOutputStream objectOutputStream) throws IOException{
        final ThreadPoolExecutor threadPool = ThreadPool.getThreadPool();
        if (FileUtil.judgeFileType((String)protocolFromSocket.getData()).equals(FileEnum.BINARY)) {
            SendFileByByte sendFileByByte = new SendFileByByte();
            // todo
        } else {
            threadPool.submit(new SendFileByLine((String)protocolFromSocket.getData()));
        }
        Port.getDataPort(protocolFromSocket.getClientIp(), String.valueOf(protocolFromSocket.getDataPort()));
        ArrayList<FileModel> fileList = FileUtil.getFileList("/home/heguicai");
        Protocol sendProtocal = new Protocol();
        sendProtocal.setData(fileList);
        objectOutputStream.writeObject(sendProtocal);
        objectOutputStream.flush();
    }

    void pause() {
    }

    public Object getFileList(String filePath) {
        return FileUtil.getFileList(filePath);
    }
}
