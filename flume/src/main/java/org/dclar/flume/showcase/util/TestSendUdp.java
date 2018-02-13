package org.dclar.flume.showcase.util;

import org.junit.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetSocketAddress;

public class TestSendUdp {


    @Test
    public void send() throws IOException {

        DatagramSocket socket = new DatagramSocket();
        byte[] data = "hello world, from mac".getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length);

        packet.setAddress(Inet4Address.getByName("centos01"));
        packet.setPort(8888);
        socket.send(packet);
        socket.close();

    }
}
