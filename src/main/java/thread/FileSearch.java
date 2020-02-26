package thread;

import bean.Property;
import bean.Query;
import socket.TCPReceiver;
import socket.UDPReceiver;
import socket.UDPSender;
import util.Config;
import util.IpAddr;

import java.util.Scanner;

public class FileSearch implements Runnable {
    private boolean flag;

    public FileSearch() {
        this.flag = true;
    }

    @Override
    public void run() {
        while (this.flag) {
            System.out.println("Please Enter the Filename You Want to Search:");
            Scanner scanner = new Scanner(System.in);
            String filename = scanner.next();
            System.out.println("Start to search " + filename);

            //SYN 请求体
            Query synQuery = new Query();
            synQuery.setFromIP(IpAddr.getLocalIpAddr());
            synQuery.setFromPort(0);
            synQuery.setToIP(Config.BROADCAST_IP);
            synQuery.setToPort(Config.SEARCH_FILE_SYN_PORT);
            synQuery.setFilename(filename);
            //ACK 接收体
            Query ackQuery = new Query();
            ackQuery.setToPort(Config.SEARCH_FILE_ACK_PORT);

            int search_times = Integer.parseInt(Property.getProperty("search_times"));
            for (int i = 0; i < search_times; i++) {
                new UDPSender().send(synQuery, 3);
                System.out.println("send SYN " + (i + 1) + " time : " + synQuery);
                new UDPReceiver().receive(ackQuery, Integer.parseInt(Property.getProperty("udp_timeout")));
                if (ackQuery.getFilename() != null && ackQuery.getFilename().equals(filename)) {
                    System.out.println("receive ACK: " + ackQuery);
                    break;
                }
            }

            if (ackQuery.getFilename() == null) {
                System.out.println("Can't find file: " + filename);
                continue;
            }

            // 创建接收文件的TCP服务器
            TCPReceiver tcpReceiver = new TCPReceiver();

            // TCP连接请求体
            Query tcpQuery = new Query();
            tcpQuery.setFromIP(IpAddr.getLocalIpAddr());
            tcpQuery.setFromPort(tcpReceiver.getPort());
            tcpQuery.setToIP(ackQuery.getFromIP());
            tcpQuery.setToPort(Config.TCP_CONNECT_PORT);
            tcpQuery.setFilename(filename);

            int tcp_connect_times = Integer.parseInt(Property.getProperty("tcp_connect_times"));
            for (int i = 0; i < tcp_connect_times; i++) {
                new UDPSender().send(tcpQuery, 3);
                boolean isSuccess = tcpReceiver.receive(tcpQuery, Integer.parseInt(Property.getProperty("tcp_timeout")));
                if (isSuccess) {
                    break;
                }
            }
        }
    }
}
