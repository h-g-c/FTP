package server;

import java.io.IOException;
import java.net.Socket;

/**
 * @author yinchao
 * @date 2020/7/3 18:54
 */
public interface Mode {

    Socket generateDataTransportSocket(Integer port) throws IOException;
}
