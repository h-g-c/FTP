package entity;

import configuration_and_constant.Constant;
import configuration_and_constant.ThreadPool;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import server.*;
import util.FileUtil;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
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

    public void upload(Protocol protocolFromSocket, ObjectOutputStream objectOutputStream) throws IOException {
        FileModel fileModel=(FileModel) protocolFromSocket.getData();
        Integer fileLength=Integer.valueOf(fileModel.getFileSize());
        if(fileModel.getFileType().equals(FileEnum.BINARY))
        {
          File file=new File(Constant.UPLOAD_PATH + fileModel.getFileName() + ".temp");
          fileModel.setFileSize(String.valueOf(file.length()));
        }else  if(fileModel.getFileType().equals(FileEnum.TEXT)){
          fileModel.setFileSize(String.valueOf(FileUtil.getFileLine(Constant.UPLOAD_PATH + fileModel.getFileName() + ".temp")));
        }
        else {// todo 文件夹不能上传
            return;
        }
        protocolFromSocket.setData(fileModel);
        objectOutputStream.writeObject(protocolFromSocket);
        objectOutputStream.writeObject(null);
        objectOutputStream.flush();
        fileModel.setFileSize(fileLength.toString());
        final ThreadPoolExecutor threadPool = ThreadPool.getThreadPool();
        if(fileModel.getFileType().equals(FileEnum.BINARY))
        {
            ReceiveFileByByte receiveFileByByte = ReceiveFileByByte.builder().fileModel(fileModel)
                    .dis(new DataInputStream(getDataSocket(protocolFromSocket.clientIp,protocolFromSocket.dataPort).getInputStream()))
                    .build();
            threadPool.submit(receiveFileByByte);
        }
        else if(fileModel.getFileType().equals(FileEnum.TEXT)){
            ReceiveFileByLine receiveFileByLine = ReceiveFileByLine.builder().fileModel(fileModel)
                    .inputStream(getDataSocket(protocolFromSocket.clientIp,protocolFromSocket.dataPort).getInputStream()).build();
            threadPool.submit(receiveFileByLine);
        }
    }

    public void download(Protocol protocolFromSocket, ObjectOutputStream objectOutputStream) throws IOException, InterruptedException {
        FileModel fileModel = (FileModel) protocolFromSocket.getData();
        String alreadySendLength = fileModel.getFileSize();
        final FileUtil fileUtil = new FileUtil();
        if (fileModel.getFileType().equals(FileEnum.BINARY)) {
            File file = new File(fileModel.getFilePath());
            long fileLength = file.length();
            fileModel.setFileSize(String.valueOf(fileLength));
        } else if(fileModel.getFileType().equals(FileEnum.TEXT)){
            fileModel.setFileSize(String.valueOf(FileUtil.getFileLine(fileModel.getFilePath())));
        }
        else {// TODO 上传的文件类型为DIR的话不做任何反应
            return;
        }
        protocolFromSocket.setData(fileModel);
        objectOutputStream.writeObject(protocolFromSocket);
        objectOutputStream.writeObject(null);
        objectOutputStream.flush();
        //传输即将发送的文件的大小给客户端
        final ThreadPoolExecutor threadPool = ThreadPool.getThreadPool();
        if (fileModel.getFileType().equals(FileEnum.BINARY)) {
            SendFileByByte sendFileByByte = SendFileByByte.builder()
                    .das(new DataOutputStream(getDataSocket(protocolFromSocket.getClientIp(), protocolFromSocket.getDataPort()).getOutputStream()))
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

    public void delete(String fileName, ObjectOutputStream objectOutputStream, Protocol protocol) throws InterruptedException, IOException, ExecutionException {
        final ThreadPoolExecutor threadPool = ThreadPool.getThreadPool();
        final Future future = threadPool.submit(new DeleteFileThread(fileName));
        protocol.setData(future.get());
        objectOutputStream.writeObject(protocol);
        objectOutputStream.writeObject(null);
        objectOutputStream.flush();
    }
}
