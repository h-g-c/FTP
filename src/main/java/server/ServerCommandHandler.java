package server;

import configuration_and_constant.ThreadPool;
import entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import util.CommonUtil;
import util.FileUtil;

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
@Slf4j(topic = "ServerCommandHandler")
@NoArgsConstructor
@Builder
public class ServerCommandHandler implements Runnable {
    Socket commandSocket;
    Socket dataTransportSocket;
    Mode mode;

    @Override
    public void run() {
        ThreadPoolExecutor threadPool = ThreadPool.getThreadPool();
        log.info("InputHandler is running...");
        if (commandSocket == null) {
            log.error("socket未建立");
            return;
        }
        try (InputStream socketInputStream = commandSocket.getInputStream(); DataInputStream dataInputStream = new DataInputStream(socketInputStream); OutputStream socketOutputStream = commandSocket.getOutputStream(); ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream); ObjectInputStream objectInputStream = new ObjectInputStream(socketInputStream);) {
            // 读入协议信息
            while (true) {
                // 读入协议信息
               Protocol protocolFromSocket = (Protocol) objectInputStream.readObject();
                // 如果是被动模式
                if (ConnectType.PASSIVE.equals(protocolFromSocket.getConnectType())) {
                    mode = new PassiveMode();
                    // 主动模式
                } else {
                    mode = new InitiativeMode();
                }
                switch (protocolFromSocket.getOperateType()) {
                    case PAUSE: {

                        break;
                    }
                    case CONNECT: {
                        mode.initialization(objectOutputStream,protocolFromSocket);
                        break;
                    }
                    case DOWNLOAD: {
                        Socket dataSocket=mode.getDataSocket(protocolFromSocket.getClientIp(),protocolFromSocket.getDataPort());
                        mode.download(protocolFromSocket,objectOutputStream,new DataOutputStream(dataSocket.getOutputStream()));
                        break;
                    }
                    case FILE_PATH: {
                        FileModel fileModel=(FileModel)protocolFromSocket.getData();
                         Protocol sendProtocal = new Protocol();
                        sendProtocal.setData( mode.getFileList(fileModel.getFilePath()));
                        objectOutputStream.writeObject(sendProtocal);
                        objectOutputStream.flush();
                        break;
                    }
                    case UPLOAD: {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error("反序列化失败");
        }
    }
}
