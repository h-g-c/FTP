package server.test;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author LvHao
 * @Description :
 * @date 2020-07-03 16:41
 */
public class Client {
    private Socket socket;
    private String nickName;

    public Client() throws IOException {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(System.in, "utf-8"));
            System.out.print("Login as:");
            this.nickName = br.readLine();

            this.socket = new Socket("localhost", 5555);
            PrintWriter printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8")), true);
            printer.println(nickName);
            //printer.close();

            new Thread(new Receiver()).start();
            new Thread(new Sender()).start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public class Receiver implements Runnable {

        @Override
        public void run() {
            BufferedReader br=null;
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
                String line;
                while ((line=br.readLine())!=null) {
                    MessageHelper.Message msg = MessageHelper.parseRawMessage(line);
                    System.out.print(msg.getFrom() + " says:");
                    System.out.println(msg.getBody());
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br!=null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (!socket.isClosed()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public class Sender implements Runnable {

        @Override
        public void run() {
            PrintWriter printer = null;
            BufferedReader reader = null;
            try {
                printer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8")), true);
                reader = new BufferedReader(new InputStreamReader(System.in, "utf-8"));
                String line;
                while ((line = reader.readLine())!=null) {
                    line = "From " + nickName + " " + line;
                    line = MessageHelper.produceRawMessage(MessageHelper.parseRawMessage(line));
                    printer.println(line);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (printer!=null) {
                    printer.close();
                }
                if (reader!=null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (!socket.isClosed()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    /**
     * @param args
     * @throws IOException
     * @throws UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        new Client();
    }

}
