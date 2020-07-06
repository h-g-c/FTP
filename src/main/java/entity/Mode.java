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
import java.io.IOException;
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

    public void download(Protocol protocolFromSocket, ObjectOutputStream objectOutputStream, DataOutputStream das) throws IOException {
        final ThreadPoolExecutor threadPool = ThreadPool.getThreadPool();
        FileModel fileModel = (FileModel) protocolFromSocket.getData();
        if (FileUtil.judgeFileType(fileModel.getFilePath()).equals(FileEnum.BINARY)) {
            SendFileByByte sendFileByByte = SendFileByByte.builder().das(das).filePath(fileModel.getFilePath()).point(Long.valueOf(fileModel.getFileSize())).build();
            threadPool.submit(sendFileByByte);
        } else {
            threadPool.submit(new SendFileByLine((String) protocolFromSocket.getData()));
        }
        Port.getDataPort(protocolFromSocket.getClientIp(), protocolFromSocket.getDataPort());
        ArrayList<FileModel> fileList = FileUtil.getFileList(Constant.DEFAULT_FILE_PATH);
        Protocol sendProtocal = new Protocol();
        sendProtocal.setData(fileList);
        objectOutputStream.writeObject(sendProtocal);
        objectOutputStream.writeObject(null);
        objectOutputStream.flush();
    }

    public void pause() {
    }

    public Object getFileList(String filePath) {
        return FileUtil.getFileList(filePath);
    }
}
