package echo.client;

import com.proto.echo.EchoRequest;
import com.proto.echo.EchoResponse;
import com.proto.echo.EchoServiceGrpc;
import com.proto.echo.EchoServiceGrpc.EchoServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class EchoClient
{

    public static void main( String[] args )
    {

        final ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();


        echoCall(channel);

        channel.shutdown();
    }

    private static void echoCall(ManagedChannel channel)
    {
        System.out.println("[client-java] making Echo RPC call");

        EchoServiceBlockingStub blockingClient = EchoServiceGrpc.newBlockingStub(channel);

        EchoResponse echoResponse = blockingClient.echo(EchoRequest.newBuilder()
                .setMsg("hello from java echo client")
                .build());

        System.out.printf("Received response from server: \"%s\"\n", echoResponse.getMsg());

    }


}
