package thread;

import bean.Query;
import socket.UDPReceiver;
import socket.UDPSender;
import util.Config;
import util.IpAddr;
import util.LocalFile;

public class SYNReceiver implements Runnable {
    private boolean flag;

    public SYNReceiver() {
        this.flag = true;
    }

    @Override
    public void run() {
        while (flag) {
            //接收外部请求文件名
            Query synQuery = new Query();
            synQuery.setToPort(Config.SEARCH_FILE_SYN_PORT);
            new UDPReceiver().receive(synQuery, 0);
            System.out.println("receive SYN: " + synQuery);

            if (new LocalFile(synQuery.getFilename()).isExist()) {
                Query sendQuery = new Query();
                sendQuery.setFromIP(IpAddr.getLocalIpAddr());
                sendQuery.setFromPort(Config.SEARCH_FILE_SYN_PORT);
                sendQuery.setToIP(synQuery.getFromIP());
                sendQuery.setToPort(Config.SEARCH_FILE_ACK_PORT);
                sendQuery.setFilename(synQuery.getFilename());
                new UDPSender().send(sendQuery, 3);
                System.out.println("send ACK: " + sendQuery);
            }
        }
    }
}
