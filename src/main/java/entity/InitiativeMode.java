package entity;

import server.Port;
import util.FileUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * @author yinchao
 * @date 2020/7/4 22:31
 */
public class InitiativeMode extends Mode {
    @Override
   public void initialization(ObjectOutputStream objectOutputStream, Protocol protocolFromSocket) throws IOException {
        Port.getDataPort(protocolFromSocket.getClientIp(),protocolFromSocket.getDataPort());
        ArrayList<FileModel> fileList = FileUtil.getFileList("/home/heguicai");
        Protocol sendProtocal = new Protocol();
        sendProtocal.setData(fileList);
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
