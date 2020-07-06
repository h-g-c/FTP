package entity;

import configuration_and_constant.ThreadPool;
import lombok.Data;
import server.SendFileByByte;
import util.FileUtil;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yinchao
 * @date 2020/7/3 18:54
 */
@Data
public abstract class Mode {
    Socket dataSocket;
    Socket commandSocket;

    public abstract void initialization(ObjectOutputStream objectOutputStream, Protocol protocolFromSocket) throws IOException;

    public abstract Socket getDataSocket(String address, Integer port);

    public void upload() {
    }

    public void download(Protocol protocolFromSocket, ObjectOutputStream objectOutputStream) throws IOException {
        FileModel fileModel = (FileModel) protocolFromSocket.getData();
        String alreadySendLength = fileModel.getFileSize();
        if (fileModel.getFileType() == FileEnum.BINARY) {
            File file = new File(fileModel.getFilePath());
            fileModel.setFileSize(String.valueOf(file.length()));
        } else {
            fileModel.setFileSize(String.valueOf(FileUtil.getFileLine(fileModel.getFilePath())));
        }
        protocolFromSocket.setData(protocolFromSocket);
        objectOutputStream.writeObject(protocolFromSocket);
        objectOutputStream.writeObject(null);
        objectOutputStream.flush();
        //传输即将发送的文件的大小给客户端

        final ThreadPoolExecutor threadPool = ThreadPool.getThreadPool();
        if (FileUtil.judgeFileType(fileModel.getFilePath()).equals(FileEnum.BINARY)) {
            SendFileByByte sendFileByByte = SendFileByByte.builder()
                    .das(new DataOutputStream(getDataSocket(protocolFromSocket.getClientIp(),protocolFromSocket.getDataPort()).getOutputStream()))
                    .filePath(alreadySendLength).point(Long.valueOf(alreadySendLength)).build();
            threadPool.submit(sendFileByByte);
        } else {
//            threadPool.submit(new SendFileByLine((String) protocolFromSocket.getData()));
        }
    }

    public void pause() {
    }

    public Object getFileList(String filePath) {
        return FileUtil.getFileList(filePath);
    }
}
