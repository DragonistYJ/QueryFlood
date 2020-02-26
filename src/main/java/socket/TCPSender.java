package socket;

import bean.Query;
import util.LocalFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TCPSender {
    public void send(Query query) {
        try {
            Socket socket = new Socket(query.getFromIP(), query.getFromPort());
            OutputStream outputStream = socket.getOutputStream();
            LocalFile localFile = new LocalFile(query.getFilename());
            if (localFile.isExist()) {
                outputStream.write(localFile.getBytes(), 0, localFile.getBytes().length);
                outputStream.flush();
            } else {
                outputStream.write("".getBytes());
            }
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
