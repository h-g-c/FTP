package entity;

import configuration_and_constant.Constant;
import configuration_and_constant.ThreadPool;
import lombok.Data;
import server.*;
import util.FileUtil;

import java.io.*;
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

    public void upload(Protocol protocolFromSocket, ObjectOutputStream objectOutputStream) throws IOException {
        FileModel fileModel=(FileModel) protocolFromSocket.getData();
        Integer fileLength=Integer.valueOf(fileModel.getFileSize());
        if(fileModel.getFileType().equals(FileEnum.BINARY))
        {
          File file=new File(Constant.UPLOAD_PATH + fileModel.getFileName() + ".temp");
          fileModel.setFileSize(String.valueOf(file.length()));
        }else {
          fileModel.setFileSize(String.valueOf(FileUtil.getFileLine(Constant.UPLOAD_PATH + fileModel.getFileName() + ".temp")));
        }
        protocolFromSocket.setData(fileModel);
        objectOutputStream.writeObject(protocolFromSocket);
        objectOutputStream.writeObject(null);
        objectOutputStream.flush();
        fileModel.setFileSize(fileLength.toString());
        final ThreadPoolExecutor threadPool = ThreadPool.getThreadPool();
        if(fileModel.getFileType().equals(FileEnum.BINARY))
        {
            ExceptFileByByte exceptFileByByte=ExceptFileByByte.builder().fileModel(fileModel)
                    .dis(new DataInputStream(getDataSocket(protocolFromSocket.clientIp,protocolFromSocket.dataPort).getInputStream()))
                    .build();
            threadPool.submit(exceptFileByByte);
        }
        else {
            ExceptFileByLine exceptFileByLine=ExceptFileByLine.builder().fileModel(fileModel)
                    .inputStream(getDataSocket(protocolFromSocket.clientIp,protocolFromSocket.dataPort).getInputStream()).build();
            threadPool.submit(exceptFileByLine);
        }
    }

    public void download(Protocol protocolFromSocket, ObjectOutputStream objectOutputStream) throws IOException, InterruptedException {
        FileModel fileModel = (FileModel) protocolFromSocket.getData();
        String alreadySendLength = fileModel.getFileSize();
        final FileUtil fileUtil = new FileUtil();
        if (FileUtil.judgeFileType(fileModel.getFilePath()).equals(FileEnum.BINARY)) {
            fileModel.setFileType(FileEnum.BINARY);
            File file = new File(fileModel.getFilePath()+".temp");
            long fileLength=file.length();
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
        if (fileModel.getFileType().equals(FileEnum.BINARY)) {
            SendFileByByte sendFileByByte = SendFileByByte.builder()
                    .das(new DataOutputStream(getDataSocket(protocolFromSocket.getClientIp(),protocolFromSocket.getDataPort()).getOutputStream()))
                    .filePath(fileModel.getFilePath()).point(Long.valueOf(alreadySendLength)).build();
            threadPool.submit(sendFileByByte);
        } else {
            SendFileByLine sendFileByLine=SendFileByLine.builder()
                    .sendOutputStream(getDataSocket(protocolFromSocket.getClientIp(),protocolFromSocket.getDataPort()).getOutputStream())
                            .fileLength(Integer.valueOf(alreadySendLength))
                            .filePath(fileModel.filePath).build();
            threadPool.submit(sendFileByLine);
        }
    }

    public void pause() {
    }

    public Object getFileList(String filePath) {
        return FileUtil.getFileList(filePath);
    }
}
