package com.tseong.learning.socket.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpBlocking {

    public static void main(String[] args) {
        try {
            ServerSocket ssc = new ServerSocket(23456);

            while (true) {
                Socket s = ssc.accept();
                System.out.println("Enter Accept:");

                try {
                    (new Thread(new Worker(s))).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Worker implements Runnable {

        private Socket socket;
        private boolean running = true;

        public Worker(Socket s) {
            this.socket = s;
        }

        @Override
        public void run() {
            try {

                InputStream is = socket.getInputStream();
                OutputStream os = socket.getOutputStream();
                while (running) {
                    byte[] b = this.readByLength(is, 1024);
                    this.process(b);
                }

            } catch (Exception t) {
                t.printStackTrace();
            }
        }

        private byte[] readByLength(InputStream is, int contLen) throws IOException {
            byte[] b = new byte[contLen];
            int off = 0;
            int length = 0;
            while ((length = is.read(b, off, contLen-off)) >=0) {// 这是一个阻塞方法read
                off += length;
                if (off >= contLen) {
                    break;
                }
            }
            return b;
        }

        private void process(byte[] b) {

        }
    }
}
