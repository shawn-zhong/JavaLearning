package com.tseong.learning.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class UdpClient {

    public static void main(String[] args) throws IOException {
        DatagramSocket client = new DatagramSocket();   // 估计是隐含了一个端口，没有明显指定而已
        //client.bind(new InetSocketAddress(9002));

        String sendStr = "HEllo, i am client";
        byte[] sendBuf;
        sendBuf = sendStr.getBytes();

        InetAddress addr = InetAddress.getByName("127.0.0.1");
        int port = 5050;
        DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, addr, port);
        client.send(sendPacket);

        byte[] recvBuf = new byte[100];
        DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
        client.receive(recvPacket);
        String recvStr = new String(recvPacket.getData(), 0, recvPacket.getLength());
        System.out.println("receive: " + recvStr);

        client.close();
    }
}
