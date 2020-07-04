package entity;

import configuration_and_constant.Constant;
import configuration_and_constant.ThreadPool;
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
 * @date 2020/7/4 22:31
 */
public class InitiativeMode extends Mode {
    @Override
    public void initialization(ObjectOutputStream objectOutputStream, Protocol protocolFromSocket) throws IOException {
        ArrayList<FileModel> fileList = FileUtil.getFileList(Constant.DEFAULT_FILE_PATH);
        Protocol sendProtocal = new Protocol();
        sendProtocal.setData(fileList);
        objectOutputStream.writeObject(sendProtocal);
        objectOutputStream.flush();
    }

    @Override
    public Socket getDataSocket(String address, Integer port) {
        try {
            super.dataSocket = new Socket(address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.dataSocket;
    }


    @Override
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
        objectOutputStream.flush();
    }

}
