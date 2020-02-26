package bean;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Query {
    private InetAddress fromIP;
    private InetAddress toIP;
    private int fromPort;
    private int toPort;
    private String filename;

    public Query() {
    }

    public void update(Query query) {
        this.fromIP = query.fromIP;
        this.toIP = query.toIP;
        this.fromPort = query.fromPort;
        this.toPort = query.toPort;
        this.filename = query.filename;
    }

    public InetAddress getFromIP() {
        return fromIP;
    }

    public void setFromIP(InetAddress fromIP) {
        this.fromIP = fromIP;
    }

    public void setFromIP(String fromIP) {
        try {
            this.fromIP = InetAddress.getByName(fromIP);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public InetAddress getToIP() {
        return toIP;
    }

    public void setToIP(InetAddress toIP) {
        this.toIP = toIP;
    }

    public void setToIP(String toIP) {
        try {
            this.toIP = InetAddress.getByName(toIP);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public int getFromPort() {
        return fromPort;
    }

    public void setFromPort(int fromPort) {
        this.fromPort = fromPort;
    }

    public int getToPort() {
        return toPort;
    }

    public void setToPort(int toPort) {
        this.toPort = toPort;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "Query{" +
                "fromIP=" + fromIP +
                ", toIP=" + toIP +
                ", fromPort=" + fromPort +
                ", toPort=" + toPort +
                ", filename='" + filename + '\'' +
                '}';
    }
}
