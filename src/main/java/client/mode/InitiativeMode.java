package client.mode;

import client.file.BinaryFileReceiveHandler;
import client.file.BinaryFileSendHandler;
import client.file.TxtFileReceiveHandler;
import client.file.TxtFileSendHandler;
import client.gui.ClientFrame;
import entity.FileEnum;
import entity.FileModel;
import entity.Protocol;
import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description : 主动模式下对服务端命令的处理
 * 重写了上传和下载方法实现了对应的功能
 * @date 2020-07-05 22:14
 */
public class InitiativeMode extends Mode {

    private FileModel fileModel;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket socket;

    @Override
    public void download(Protocol protocolFromSocket, ClientFrame clientFrame,ArrayList<String[]> data) {
        try{
            if(clientFrame.getDataSocket() != null){
                socket = clientFrame.getDataSocket().accept();
                inputStream = socket.getInputStream();
                fileModel = (FileModel)protocolFromSocket.getData();
                if(fileModel.getFileType().equals(FileEnum.BINARY)){
                    BinaryFileReceiveHandler.receiveBinaryFile(inputStream,fileModel,clientFrame,data);
                }else{
                    TxtFileReceiveHandler.receiveTxtFile(inputStream,fileModel,clientFrame,data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void upload(Protocol protocolFromSocket, ClientFrame clientFrame,ArrayList<String[]> data){
        try{
            if(clientFrame.getDataSocket() != null){
                socket = clientFrame.getDataSocket().accept();
                outputStream=socket.getOutputStream();
                fileModel = (FileModel)protocolFromSocket.getData();
                if(fileModel.getFileType().equals(FileEnum.BINARY)){
                    BinaryFileSendHandler.sendBinaryFile(outputStream,fileModel,clientFrame,data);
                }else if(fileModel.getFileType().equals(FileEnum.TEXT)){
                    TxtFileSendHandler.sendTxtFile(outputStream,fileModel,clientFrame,data);
                }else{
                    //TODO something
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
