package servertest.test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-03 16:40
 */
public class Server {
    private static Map<String, Socket> sockets = new HashMap<String, Socket>();

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5555);
        while (true) {
            Socket socket = server.accept();
            try {
                InputStream input = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(input, "utf-8"));
                String nickName =br.readLine();
                sockets.put(nickName.trim().toLowerCase(), socket);
                System.out.println(nickName + " connects to server.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            new MessageSwitchService(socket).start();
        }
    }

    public static class MessageSwitchService extends Thread {
        private Socket socket;
        public MessageSwitchService(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {

                InputStream input = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(input, "utf-8"));
                String line;
                while ((line=br.readLine())!=null) {
                    System.out.println(line);
                    MessageHelper.Message msg = MessageHelper.parseRawMessage(line);
                    Socket target = sockets.get(msg.getTo().toLowerCase());
                    PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(target.getOutputStream(), "utf-8")), true);
                    printer.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("close");
        }

    }

}