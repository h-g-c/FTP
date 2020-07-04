package client.command;

import client.gui.ClientFrame;
import client.gui.panel.ServerFilePanel;
import client.socket.CreatServer;
import entity.Protocol;
import lombok.*;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.net.Socket;

/**
 * @author LvHao
 * @Description :处理客户端命令接受的线程
 * @date 2020-07-04 18:11
 */
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class ClientCommandHandler implements Runnable{

    @NonNull
    private ClientFrame clientFrame;


    private final String[] tableInfo = {"文件名","大小","日期"};
    private ServerFilePanel serverFilePanel;
    private DefaultTableModel model;

    @Override
    public void run() {
        Socket socket = clientFrame.socket;
        while (true){
            String[][] def = {
                    {"Asdasd","Adsadas","Asdas"},
                    {"asdasd","asdasd","asdsad"}
            };
            if(socket.isConnected()){
                serverFilePanel = clientFrame.getJPanel3().getJPanel2();
                model = serverFilePanel.getModel();
                try(InputStream socketInputStream = socket.getInputStream();
                    DataInputStream dataInputStream = new DataInputStream(socketInputStream);
                    OutputStream socketOutputStream = socket.getOutputStream();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    ObjectInputStream objectInputStream = new ObjectInputStream(socketInputStream)){

                    //读入服务端命令的协议
                    Protocol protocolFromSocket = (Protocol) objectInputStream.readObject();

                    //如果是主动模式
                    if(true){

                        //接下来判断命令的具体动作
                        new Thread(new CreatServer(protocolFromSocket,socket)).start();
                        //TODO something
                        model.setRowCount(0);
                        model = new DefaultTableModel(def,tableInfo);
                        serverFilePanel.getJTable().setModel(model);
                        serverFilePanel.getJTextField().setText("asdsa");
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
