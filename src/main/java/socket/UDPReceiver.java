package socket;

import bean.Query;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class UDPReceiver {
    public void receive(Query query, int timeout) {
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(query.getToPort());
            datagramSocket.setSoTimeout(timeout);
            byte[] bytes = new byte[4096];
            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
            datagramSocket.receive(datagramPacket);
            query.update(new Gson().fromJson(new String(bytes, 0, datagramPacket.getLength()), Query.class));
            datagramSocket.close();
        } catch (SocketTimeoutException e) {
            datagramSocket.close();
            System.out.println("udp receive timeout: " + query);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
