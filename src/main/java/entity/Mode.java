package entity;

import configuration_and_constant.Constant;
import configuration_and_constant.ThreadPool;
import lombok.Data;
import server.DatabaseService;
import server.Port;
import server.SendFileByByte;
import server.SendFileByLine;
import util.FileUtil;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yinchao
 * @date 2020/7/3 18:54
 */
@Data
public abstract class Mode {
    Socket dataSocket;
    Socket commandSocket;

    public boolean authenticate(Protocol protocol) {
        final User user = (User) protocol.getData();
        DatabaseService databaseService = new DatabaseService();
        return databaseService.isValidUser(user);
    }

    public void sendAuthenticateFailMessage(ObjectOutputStream objectOutputStream, Protocol protocol) throws IOException {
        protocol.setData("用户名或密码失败，请重试");
        protocol.setOperateType(OperateType.ERROR);
        objectOutputStream.writeObject(protocol);
        objectOutputStream.writeObject(null);
    }

    public abstract void initialize(ObjectOutputStream objectOutputStream, Protocol protocolFromSocket) throws IOException;

    public abstract Socket getDataSocket(String address, Integer port);

    public void upload() {
    }

    public void download(Protocol protocolFromSocket, ObjectOutputStream objectOutputStream) throws IOException, InterruptedException {
        FileModel fileModel = (FileModel) protocolFromSocket.getData();
        String alreadySendLength = fileModel.getFileSize();
        final FileUtil fileUtil = new FileUtil();
        if (fileUtil.judgeFileType(fileModel.getFilePath()).equals(FileEnum.BINARY)) {
            File file = new File(fileModel.getFilePath());
            long fileLength=file.length();
            fileModel.setFileSize(String.valueOf(fileLength));
        } else {
            fileModel.setFileSize(String.valueOf(FileUtil.getFileLine(fileModel.getFilePath())));
        }
        protocolFromSocket.setData(fileModel);
        objectOutputStream.writeObject(protocolFromSocket);
        objectOutputStream.writeObject(null);
        objectOutputStream.flush();
        //传输即将发送的文件的大小给客户端
        final ThreadPoolExecutor threadPool = ThreadPool.getThreadPool();
        if (fileUtil.judgeFileType(fileModel.getFilePath()).equals(FileEnum.BINARY)) {
            SendFileByByte sendFileByByte = SendFileByByte.builder()
                    .das(new DataOutputStream(getDataSocket(protocolFromSocket.getClientIp(),protocolFromSocket.getDataPort()).getOutputStream()))
                    .filePath(fileModel.getFilePath()).point(Long.valueOf(alreadySendLength)).build();
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
