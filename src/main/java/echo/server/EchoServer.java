package echo.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class EchoServer
{



    public static void main( String[] args ) throws IOException, InterruptedException
    {
        System.out.println("[server-java] starting Echo server ..");

        Server server = ServerBuilder
                .forPort(50051)
                .addService(new EchoServiceImpl())
                .build();


        server.start();


        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> server.shutdown()));

        server.awaitTermination();
    }

}
