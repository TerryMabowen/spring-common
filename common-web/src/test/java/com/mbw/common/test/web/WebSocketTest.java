package com.mbw.common.test.web;

import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-06-17 10:18
 */
public class WebSocketTest {

    @Test
    public void test1() {
        f1();
    }


    private void f1() {
        for (int i = 0; i <= 255; i++) {
           String ip = "192.168.100." + i;
            boolean flag = f2(ip);
            if (flag) {
                System.out.println("Success IP:  ---- " + ip);
                break;
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("Thread Sleep Error " + e.getMessage());
            }
        }
    }

    private boolean f2(String ip) {
        try {
            InetAddress host = InetAddress.getByName(ip);
            int port = 6789;
            int timeout = 10000;
            return testConnection(host, port, timeout);
        } catch (UnknownHostException e) {
            System.out.println("ERROR UnknownHostException");
            return false;
        }
    }

    private boolean testConnection(InetAddress host, int port, int timeout) {
        boolean isReachable = false;
        Socket socket = null;
        try {
            socket = new Socket();
            InetSocketAddress endpointSocketAddr = new InetSocketAddress(host, port);
            socket.connect(endpointSocketAddr, timeout);
            System.out.println("SUCCESS - remote: " + host.getHostAddress() + " port " + port);
            isReachable = true;
        } catch (IOException e) {
            System.out.println("FAILED - remote: " + host.getHostAddress() + " port " + port);
            isReachable = false;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("ERROR occurred while closing socket..");
                    isReachable = false;
                }
            }
        }

        return isReachable;
    }
}
