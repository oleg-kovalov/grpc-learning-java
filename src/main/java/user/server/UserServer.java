package user.server;

import echo.server.EchoServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class UserServer
{

    public static void main( String[] args ) throws IOException, InterruptedException
    {
        System.out.println("[server-java] starting User server ..");

        Server server = ServerBuilder
                .forPort(50051)
                .addService(new UserServiceImpl())
                .build();

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(
                () -> server.shutdown()));

        server.awaitTermination();
    }

}
