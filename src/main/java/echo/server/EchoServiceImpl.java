package echo.server;

import com.proto.echo.EchoRequest;
import com.proto.echo.EchoResponse;
import com.proto.echo.EchoServiceGrpc;
import io.grpc.stub.StreamObserver;

public class EchoServiceImpl extends EchoServiceGrpc.EchoServiceImplBase
{
    @Override
    public void echo( EchoRequest request, StreamObserver<EchoResponse> responseObserver )
    {
        System.out.println("[server-java] Echo operation was invoked");

        responseObserver.onNext(EchoResponse.newBuilder()
                        .setMsg("server responding to : " + request.getMsg())
                        .build());

        responseObserver.onCompleted();
    }
}
