package server;

import entity.FileModel;
import configuration_and_constant.ThreadPool;
import entity.FileEnum;
import entity.Protocol;
import entity.TransmissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import util.CommonUtil;
import util.FileUtil;
import util.FileUtil;
import util.GenerateDataSocket;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 服务端处理命令输入的线程
 *
 * @author yinchao
 * @date 2020/7/2 17:13
 */
@AllArgsConstructor
@Data
@Slf4j(topic = "InputHandler")
@NoArgsConstructor
@Builder
public class ServerCommandHandler implements Runnable {
    Socket commandSocket;
    Socket dataTransportSocket;

    @Override
    public void run() {
        ThreadPoolExecutor threadPool = ThreadPool.getThreadPool();
        while (true) {
            log.info("InputHandler is running...");
            if (commandSocket == null) {
                log.error("socket未建立");
                return;
            }
            try (InputStream socketInputStream = commandSocket.getInputStream();
                 DataInputStream dataInputStream = new DataInputStream(socketInputStream);
                 OutputStream socketOutputStream = commandSocket.getOutputStream();
                 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                 ObjectInputStream objectInputStream = new ObjectInputStream(socketInputStream);) {
                // 读入协议信息
                Protocol protocolFromSocket = (Protocol) objectInputStream.readObject();
                // 如果是被动模式
                if (TransmissionType.PASSIVE.equals(protocolFromSocket.getTransmissionType())) {
                    // 产生一个随机端口
                    Integer port = CommonUtil.generateRandomPort();
                    // 构造协议信息(server 数据端口号)
                    Protocol sendProtocal = Protocol.builder().dataPort(port).transmissionType(TransmissionType.PASSIVE).build();
                    objectOutputStream.writeObject(protocolFromSocket);
                    objectOutputStream.flush();
                    // 等待客户端建立 data 端口
                    dataTransportSocket = GenerateDataSocket.inPassiveMode(port);
                    // 发送文件列表
                    sendProtocal = Protocol.builder().data(FileUtil.getFileList("/")).transmissionType(TransmissionType.PASSIVE).build();
                    objectOutputStream.writeObject(sendProtocal);
                    objectOutputStream.flush();
                    // 主动模式
                } else if (TransmissionType.INITIATIVE.equals(protocolFromSocket.getTransmissionType())) {
                    Port.getDataPort(protocolFromSocket.getClientIp(),protocolFromSocket.getDataPort());
                    ArrayList<FileModel> fileList= FileUtil.getFileList("/home/heguicai");
                    Protocol sendProtocal =new Protocol();
                    sendProtocal.setData(fileList);
                    objectOutputStream.writeObject(sendProtocal);
                    objectOutputStream.flush();
                    // 开始下载
                }else if(TransmissionType.DOWNLOAD.equals(protocolFromSocket.getTransmissionType())){
                    if(FileUtil.judgeFileType(protocolFromSocket.getMessage()).equals(FileEnum.BINARY)) {
                        SendFileByByte sendFileByByte = new SendFileByByte();
                        // todo
                    }else {
                        threadPool.submit(new SendFileByLine(protocolFromSocket.getMessage()));
                    }
                    Port.getDataPort(protocolFromSocket.getClientIp(),protocolFromSocket.getDataPort());
                    ArrayList<FileModel> fileList= FileUtil.getFileList("/home/heguicai");
                    Protocol sendProtocal =new Protocol();
                    sendProtocal.setData(fileList);
                    objectOutputStream.writeObject(sendProtocal);
                    objectOutputStream.flush();
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            } catch (ClassNotFoundException e) {
                log.error("反序列化失败");
            }
        }
    }
}
