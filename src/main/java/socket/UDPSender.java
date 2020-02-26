package socket;

import bean.Query;
import com.google.gson.Gson;
import util.IpAddr;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSender {
    public void send(Query query, int times) {
        String content = new Gson().toJson(query);
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            for (int i = 0; i < times; i++) {
                DatagramPacket packet = new DatagramPacket(content.getBytes(), content.getBytes().length, query.getToIP(), query.getToPort());
                datagramSocket.send(packet);
            }
            datagramSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}