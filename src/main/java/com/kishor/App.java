package com.kishor;

import java.util.List;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;

public class App {
    public static void main(String[] args) {
        System.out.println("Cybersecurity Tool Starting...");
        try {
            startPacketCapture();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startPacketCapture() throws PcapNativeException, NotOpenException {
        List<PcapNetworkInterface> devices = Pcaps.findAllDevs();
        if (devices.isEmpty()) {
            System.out.println("No network devices found.");
            return;
        }

        PcapNetworkInterface device = devices.get(0); // Use the first available device
        System.out.println("Using device: " + device.getName());

        int snapLen = 65536;  // Capture all packets, no truncation
        int timeout = 10;     // Timeout in milliseconds
        PcapHandle handle = device.openLive(snapLen, PcapNetworkInterface.PromiscuousMode.PROMISCUOUS, timeout);

        PacketListener listener = packet -> System.out.println("Captured packet: " + packet);

        System.out.println("Starting packet capture...");
        try {
            handle.loop(10, listener); // Capture 10 packets, then stop
        } catch (PcapNativeException | NotOpenException | InterruptedException e) {
            e.printStackTrace();
        } // Capture 10 packets, then stop
        handle.close();
    }
}
