package util;

import entity.TransmissionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

/**
 * 服务端处理命令输入线程
 * @author yinchao
 * @date 2020/7/2 17:13
 */
@AllArgsConstructor
@Data
@Slf4j(topic = "InputHandler")
public class ServerInputHandler implements Runnable {
    Socket socket;

    @Override
    public void run() {
        log.info("InputHandler is running...");
        if (socket == null) {
            log.error("socket未建立");
            return;
        }
        try(InputStream socketInputStream = socket.getInputStream(); DataInputStream dataInputStream = new DataInputStream(socketInputStream)) {
            // 读入命令长度
            int length = dataInputStream.readInt();
            byte[] bytes = new byte[length];
            // 读入数据长度
            dataInputStream.readFully(bytes);
            // 得到制定的主动/被动模式字符串
            String transmissionType = new String(bytes);
            System.out.println(TransmissionType.valueOf(transmissionType));
            // todo: 被动模式的主要实现
        }catch (IOException e){
            log.error(e.getMessage());
        }
    }
}
