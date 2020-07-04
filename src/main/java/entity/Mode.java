package entity;

import configuration_and_constant.ThreadPool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.Port;
import server.SendFileByByte;
import server.SendFileByLine;
import util.FileUtil;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yinchao
 * @date 2020/7/3 18:54
 */
@Data
public abstract class Mode {
    Socket dataSocket;
    Socket commandSocket;

   public abstract void initialization(ObjectOutputStream objectOutputStream, Protocol protocolFromSocket) throws IOException;

   public abstract Socket getDataSocket(String address, Integer port);

   public void upload() {
    }

   public   void download(Protocol protocolFromSocket, ObjectOutputStream objectOutputStream, DataOutputStream das) throws IOException
   {

   }

    public void pause() {
    }

    public Object getFileList(String filePath) {
        return FileUtil.getFileList(filePath);
    }
}
