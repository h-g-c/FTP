package server;

import entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
        log.info("InputHandler is running...");
        if (commandSocket == null) {
            log.error("socket未建立");
            return;
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(commandSocket.getOutputStream()); ObjectInputStream objectInputStream = new ObjectInputStream(commandSocket.getInputStream())) {
            // 读入协议信息
            Protocol protocolFromSocket = (Protocol) objectInputStream.readObject();
            // 如果是被动模式
            if (ConnectType.PASSIVE.equals(protocolFromSocket.getConnectType())) {
                mode = new PassiveMode();
                // 主动模式
            } else {
                mode = new InitiativeMode();
            }
            while (true) {
                switch (protocolFromSocket.getOperateType()) {
                    case PAUSE: {
                        mode.pause();
                        break;
                    }
                    case CONNECT: {
                        mode.initialization(objectOutputStream, protocolFromSocket);
                        break;
                    }
                    case DOWNLOAD: {
                        Socket dataSocket = mode.getDataSocket(protocolFromSocket.getClientIp(), protocolFromSocket.getDataPort());
                        mode.download(protocolFromSocket, objectOutputStream, new DataOutputStream(dataSocket.getOutputStream()));
                        break;
                    }
                    case FILE_PATH: {
                        System.out.println((String) protocolFromSocket.getData());
                        mode.getFileList((String) protocolFromSocket.getData());
                        break;
                    }
                    case UPLOAD: {
                        mode.upload();
                        break;
                    }
                }
                // 读入协议信息
                protocolFromSocket = (Protocol) objectInputStream.readObject();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error("反序列化失败");
        }
    }
}
