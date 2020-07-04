package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.CommonUtil;
import util.FileUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author yinchao
 * @date 2020/7/4 22:32
 */
public class PassiveMode extends Mode{
    @Override
    public void initialization(ObjectOutputStream objectOutputStream, Protocol protocolFromSocket) throws IOException {
        Integer port = CommonUtil.generateRandomPort();
        // 构造协议信息(server 数据端口号)
        Protocol sendProtocal = Protocol.builder().dataPort(port).operateType(OperateType.CONNECT).connectType(ConnectType.PASSIVE).build();
        objectOutputStream.writeObject(protocolFromSocket);
        objectOutputStream.flush();
        // 等待客户端建立 data 端口
        Socket dataTransportSocket = new GenerateDataSocket().generateInPassiveMode(port);
        // 发送文件列表
        sendProtocal = Protocol.builder().data(FileUtil.getFileList("/")).operateType(OperateType.CONNECT).build();
        objectOutputStream.writeObject(sendProtocal);
        objectOutputStream.flush();
    }

    @Override
    public void getDataPort() {

    }


    @Override
    public Object getFileList(String filePath) {
        return null;
    }
}
