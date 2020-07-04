package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-03 10:51
 */

public class Server {
    // 存放服务端接收到的客户端类（可以进行操作）的队列
    public ArrayList<ClientServer> clientServer;

    public Server() {
        // TODO Auto-generated constructor stub
        // 初始化队列
        clientServer = new ArrayList<ClientServer>();
    }

    public void initServer() {
        try {
            // 创建监听的端口号
            ServerSocket s = new ServerSocket(21);
            System.out.println("启动服务器.....");
            System.out.println("等待客户机进入.......");
            // 让server进入阻塞状态（等待客户机的进入），有客户机连接上此端口的server客户端就会返回一个socket对象，服务器进行接受
            // 不断的去接受客户端发来的请求，并将接受到的socket放到队列中
            while (true) {
                Socket ss = s.accept();
                clientServer.add(new ClientServer(ss));
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.initServer();

    }

    /**
     * 操作类，接受了socket，进行服务端对客户端的操作
     *
     * @author M_WBCG
     *
     */
    class ClientServer {
        Socket ss;

        public ClientServer(Socket ss) throws IOException {
            // TODO Auto-generated constructor stub
            this.ss = ss;

            while (true){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String s = bufferedReader.readLine();
                PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(ss.getOutputStream())), true);
                printer.println(s);
            }
            // socket的输出流（该流为字节流）
//            OutputStream out;
//            try {
//                out = ss.getOutputStream();
//                String msg = "服务器说你好\n";
//                out.write(msg.getBytes());
//                // 将流和socket关掉，不关掉客户端在未接受到消息的时候会断开连接
//                out.close();
//                ss.close();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            AcceptTest acceptTest = new AcceptTest(ss);
//            new Thread(acceptTest).start();
        }
    }
}