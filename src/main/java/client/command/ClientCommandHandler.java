package client.command;

import client.gui.ClientFrame;
import client.gui.panel.ServerFilePanel;
import client.socket.CreatServer;
import entity.Protocol;
import entity.TransmissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private ClientFrame clientFrame;

    @Override
    public void run() {
        Socket socket = clientFrame.socket;
        ServerFilePanel serverFilePanel = clientFrame.getJPanel3().getJPanel2();
        while (true){
            if(socket.isConnected()){
                try(InputStream socketInputStream = socket.getInputStream();
                    DataInputStream dataInputStream = new DataInputStream(socketInputStream);
                    OutputStream socketOutputStream = socket.getOutputStream();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    ObjectInputStream objectInputStream = new ObjectInputStream(socketInputStream)){

                    //读入服务端命令的协议
                    Protocol protocolFromSocket = (Protocol) objectInputStream.readObject();

                    //如果是主动模式
                    if(TransmissionType.INITIATIVE.equals(protocolFromSocket.getTransmissionType())){

                        //接下来判断命令的具体动作
                        new Thread(new CreatServer(protocolFromSocket,socket)).start();
                        //TODO something
                        serverFilePanel.setData(null);
                        serverFilePanel.setData(null);
                        serverFilePanel.updateUI();
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
