package socket;

import bean.Property;
import bean.Query;
import util.IpAddr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPReceiver {
    private ServerSocket serverSocket;

    public TCPReceiver() {
        try {
            this.serverSocket = new ServerSocket(0, 1, IpAddr.getLocalIpAddr());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return this.serverSocket.getLocalPort();
    }

    public boolean receive(Query query, int timeout) {
        try {
            this.serverSocket.setSoTimeout(timeout);
            Socket socket = this.serverSocket.accept();

            // 非目标主机的连接
            if (!socket.getInetAddress().equals(query.getToIP())) {
                System.out.println("Other PC " + socket.getInetAddress() + " connection, disconnect" + query);
                return false;
            }

            // 文件已经存在
            File file = new File(Property.getProperty("download_dir") + query.getFilename());
            if (file.exists()) {
                System.out.println("File " + query.getFilename() + " already exists, please remove it.");
                return false;
            }
            file.createNewFile();

            // 写入文件
            InputStream inputStream = socket.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int l = 0;
            while ((l = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, l);
            }
            outputStream.close();
            inputStream.close();
            socket.close();
            System.out.println("File has been saved");
        } catch (SocketTimeoutException e) {
            System.out.println("TCP connect timeout: " + query);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
