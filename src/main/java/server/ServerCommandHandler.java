package server;

import entity.Protocol;
import entity.TransmissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import util.CommonUtil;

import java.io.*;
import java.net.Socket;

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
                Protocol protocolFromSocket = CommonUtil.readProtocolFromSocket(objectInputStream);
                // 如果是被动模式
                if (TransmissionType.PASSIVE.equals(protocolFromSocket.getTransmissionType())) {
                    ServerPassiveMode mode = new ServerPassiveMode();
                    Integer port = CommonUtil.generateRandomPort();
                    Protocol sendProtocal = Protocol.builder().dataPort(port).transmissionType(TransmissionType.PASSIVE).build();
                    objectOutputStream.writeObject(protocolFromSocket);
                    objectOutputStream.flush();
                    dataTransportSocket = mode.generateDataTransportSocket(port);
                    sendProtocal = Protocol.builder().message("数据端口已建立").transmissionType(TransmissionType.PASSIVE).build();
                    objectOutputStream.writeObject(sendProtocal);
                    objectOutputStream.flush();
                } else if (TransmissionType.INITIATIVE.equals(protocolFromSocket.getTransmissionType())) {
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            } catch (ClassNotFoundException e) {
                log.error("反序列化失败");
            }
        }
    }
}
