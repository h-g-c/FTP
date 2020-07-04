package client.command;

import client.socket.CreatServer;
import entity.Protocol;
import entity.TransmissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.CommonUtil;

import java.io.*;
import java.net.Socket;

/**
 * @author LvHao
 * @Description :处理客户端命令接受的线程
 * @date 2020-07-04 18:11
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClientCommandHandler implements Runnable{

    private Socket socket;

    @Override
    public void run() {
        while (true){
            if(socket.isConnected()){
                try(InputStream socketInputStream = socket.getInputStream();
                    DataInputStream dataInputStream = new DataInputStream(socketInputStream);
                    OutputStream socketOutputStream = socket.getOutputStream();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    ObjectInputStream objectInputStream = new ObjectInputStream(socketInputStream)){

                    //读入服务端命令的协议
                    Protocol protocolFromSocket = CommonUtil.readProtocolFromSocket(objectInputStream);

                    //如果是主动模式
                    if(TransmissionType.INITIATIVE.equals(protocolFromSocket.getTransmissionType())){

                        //接下来判断命令的具体动作
                        new Thread(new CreatServer(protocolFromSocket,socket)).start();
                        //TODO something
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
