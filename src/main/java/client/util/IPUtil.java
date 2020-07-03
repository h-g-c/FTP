package client.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-03 11:43
 */
public class IPUtil {
    public static String getLocalIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
