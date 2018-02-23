package com.tseong.learning.thrift.client;

import com.tseong.learning.thrift.HelloWorldService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class HelloWorldClient {

    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 8099;
    public static final int TIMEOUT = 30000;

    public static void main(String[] args) {
        HelloWorldClient client = new HelloWorldClient();
        client.startClient("shawnz");
    }

    public void startClient(String userName) {
        TTransport transport = null;
        try {
          transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);

            // 协议要和服务器端一致
          TProtocol protocol = new TBinaryProtocol(transport);
         // TProtocol tProtocol = new TCompactProtocol(transport);
         // TProtocol tProtocol1 = new TJSONProtocol(transport);
           HelloWorldService.Client client = new HelloWorldService.Client(protocol);
           transport.open();
           String result = client.sayHello(userName);
           System.out.println("Result from Thrift server : " + result);

        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }

}
