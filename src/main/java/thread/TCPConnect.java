package thread;

import bean.Query;
import socket.TCPSender;
import socket.UDPReceiver;
import util.Config;

public class TCPConnect implements Runnable {
    private boolean flag;

    public TCPConnect() {
        this.flag = true;
    }

    @Override
    public void run() {
        while (flag) {
            Query tcpQuery = new Query();
            tcpQuery.setToPort(Config.TCP_CONNECT_PORT);
            new UDPReceiver().receive(tcpQuery, 0);
            System.out.println("receive TCP connect: " + tcpQuery);
            new TCPSender().send(tcpQuery);
        }
    }
}
