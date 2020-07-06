package client.socket;

import client.command.SendCommand;
import client.gui.ClientFrame;
import client.util.IPUtil;
import configuration_and_constant.Constant;
import entity.ConnectType;
import entity.FileModel;
import entity.OperateType;
import entity.Protocol;
import lombok.*;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author LvHao
 * @Description :客户端随机打开一个端口接受数据 并将这个端口信息发给服务端 等待服务端连接
 * @date 2020-07-04 10:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class CreatServer implements Runnable{
    private Protocol protocolLocal;

    /**
     * 服务端相关数据
     */
    @NonNull
    private Protocol protocolServer;

    /**
     * 当前命令端口的socket信息
     */
    @NonNull
    private Socket socketLocal;

    @NonNull ClientFrame clientFrame;

    @NonNull
    private String filePath;
    private String[] fileName;

    /**
     * 新建数据传输端口的socket信息
     */
    private Socket socketServer;

    private final String defPath = "D:\\下载";
    @Override
    public void run() {
        ServerSocket serverSocket = null;
        protocolLocal = new Protocol();
        while(true){
            try {
                serverSocket = new ServerSocket(0);



                protocolLocal.setOperateType(OperateType.DOWNLOAD);
                protocolLocal.setClientIp(IPUtil.getLocalIP());
                protocolLocal.setConnectType(ConnectType.INITIATIVE);
                protocolLocal.setDataPort(serverSocket.getLocalPort());
                SendCommand.sendCommend(protocolLocal,clientFrame.getSocket(),clientFrame.getSocketObjectOutputStream());


                socketLocal = serverSocket.accept();

                FileModel fileModel=(FileModel) protocolLocal.getData();
                DataInputStream dis=new DataInputStream(socketLocal.getInputStream());
                long fileLength = dis.readLong();
                File file = new File(defPath+fileModel.getFileName()+ ".temp");
                RandomAccessFile rad = new RandomAccessFile(defPath + fileModel.getFileName() + ".temp", "rw");
                long size = 0;
                if (file.exists() && file.isFile()) {
                    size = file.length();
                }
                //从之前的断点的地方进行传输；
                rad.seek(size);
                byte[] value = new byte[1024 * 8];
                while (true) {
                    int length = dis.read(value);
                    if (length == -1) {
                        break;
                    }
                    rad.write(value);
                    size += length;
                    if (size == fileLength) {
                        break;
                    }
                }
                dis.close();
                rad.close();
                //文件重命名
                if (size >= fileLength) {
                    file.renameTo(new File(Constant.DEFAULT_FILE_PATH + "/" + fileModel.getFileName()));
                }
                dis.close();
                rad.close();


                socketLocal.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(!socketLocal.isClosed()){
                    try{
                        socketLocal.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
