package client.mode;

import client.file.BinaryFileReceiveHandler;
import client.gui.ClientFrame;
import client.socket.CreatServer;
import entity.FileEnum;
import entity.FileModel;
import entity.Protocol;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author LvHao
 * @Description : 主动模式下对服务端命令的处理
 * @date 2020-07-05 22:14
 */
public class InitiativeMode extends Mode {

    private FileModel fileModel;
    private InputStream inputStream;
    private Socket socket;

    @Override
    public void download(Protocol protocolFromSocket, ClientFrame clientFrame) {
        try{
            if(clientFrame.getDataSocket() != null){
                System.out.println("socket connect");
                socket = clientFrame.getDataSocket().accept();
                inputStream = socket.getInputStream();
                fileModel = (FileModel)protocolFromSocket.getData();
                if(fileModel.getFileType().equals(FileEnum.BINARY)){
                    BinaryFileReceiveHandler.receiveBinaryFile(inputStream,fileModel,clientFrame);
                }else{
                    //TODO something
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
