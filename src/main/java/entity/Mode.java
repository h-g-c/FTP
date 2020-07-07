package entity;

import configuration_and_constant.ThreadPool;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import server.DatabaseService;
import server.SendFileByByte;
import util.FileUtil;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yinchao
 * @date 2020/7/3 18:54
 */
@Data
@Slf4j
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
        if (FileUtil.judgeFileType(fileModel.getFilePath()).equals(FileEnum.BINARY)) {
            fileModel.setFileType(FileEnum.BINARY);
            File file = new File(fileModel.getFilePath());
            long fileLength = file.length();
            fileModel.setFileSize(String.valueOf(fileLength));
        } else {
            fileModel.setFileType(FileEnum.TEXT);
            fileModel.setFileSize(String.valueOf(FileUtil.getFileLine(fileModel.getFilePath())));
        }
        protocolFromSocket.setData(fileModel);
        objectOutputStream.writeObject(protocolFromSocket);
        objectOutputStream.writeObject(null);
        objectOutputStream.flush();
        System.out.println(protocolFromSocket.toString());
        //传输即将发送的文件的大小给客户端
        final ThreadPoolExecutor threadPool = ThreadPool.getThreadPool();
        if (FileUtil.judgeFileType(fileModel.getFilePath()).equals(FileEnum.BINARY)) {
            SendFileByByte sendFileByByte = SendFileByByte.builder()
                    .das(new DataOutputStream(getDataSocket(protocolFromSocket.getClientIp(), protocolFromSocket.getDataPort()).getOutputStream()))
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

    public void delete(File file) {
        if (!file.exists()) {
            log.info("文件不存在");
            return ;
        }
        if (file.isFile()) {
            if (file.delete()) {
                log.info("删除文件{}成功", file.getAbsolutePath());
            } else {
                log.error("删除失败");
            }
            // 如果是文件夹,递归删除
        } else {
            Arrays.stream(file.listFiles()).forEach(item -> {
                delete(item);
                item.delete();
            });
            file.delete();
        }
    }
}
