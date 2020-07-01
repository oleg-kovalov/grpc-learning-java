package user.server;

import com.proto.user.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.*;

public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase
{

    static final List<User> users = new ArrayList<>();

    @Override
    public void listUsers( ListUsersRequest request, StreamObserver<ListUsersResponse> responseObserver )
    {
        System.out.println("[server-java] ListUsers operation was invoked");

        responseObserver.onNext(ListUsersResponse.newBuilder()
                .addAllUsers(users)
                .build());

        responseObserver.onCompleted();
    }

    @Override
    public void getUser( GetUserRequest request, StreamObserver<GetUserResponse> responseObserver )
    {
        System.out.println("[server-java] GetUser operation was invoked");

        final String userId = request.getUserId();

        User userWithId = users.stream()
                .filter(user -> userId.equals(user.getId()))
                .findAny()
                .orElse(null);

        if (userWithId == null) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("user with ID " + userId + " was not found")
                    .asRuntimeException());
            return;
        }

        responseObserver.onNext(GetUserResponse.newBuilder()
                .setUser(userWithId)
                .build());

        responseObserver.onCompleted();
    }

    @Override
    public void addUser( AddUserRequest request, StreamObserver<AddUserResponse> responseObserver )
    {
        System.out.println("[server-java] AddUser operation was invoked");

        User userWithId = request.getUser().toBuilder().setId(UUID.randomUUID().toString()).build();
        users.add(userWithId);

        responseObserver.onNext(AddUserResponse.newBuilder()
                .setUser(userWithId)
                .build());

        responseObserver.onCompleted();
    }

    @Override
    public void updateUser( UpdateUserRequest request, StreamObserver<UpdateUserResponse> responseObserver )
    {
        System.out.println("[server-java] UpdateUser operation was invoked");

        final String userId = request.getUser().getId();

        User userWithId = users.stream()
                .filter(user -> userId.equals(user.getId()))
                .findAny()
                .orElse(null);

        if (userWithId == null) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("user with ID " + userId + " was not found")
                    .asRuntimeException());
            return;
        }

        User.Builder builder = userWithId.toBuilder();

        for (String path : request.getUpdateMask().getPathsList())
        {
            RuntimeException exception = null;

            switch (path){
                case "email":
                    builder.setEmail(request.getUser().getEmail());
                    break;
                default:
                    exception = Status.INVALID_ARGUMENT
                                    .withDescription("could not update field " + path + " on user")
                                    .asRuntimeException();
                    break;
            }

            if (exception != null){
                responseObserver.onError(exception);
            }
        }

        User updatedUser = builder.build();

        responseObserver.onNext(UpdateUserResponse.newBuilder()
                .setUser(updatedUser)
                .build());

        responseObserver.onCompleted();
    }

    @Override
    public void deleteUser( DeleteUserRequest request, StreamObserver<DeleteUserResponse> responseObserver )
    {
        System.out.println("[server-java] DeleteUser operation was invoked");

        final String userId = request.getUserId();

        User userWithId = users.stream()
                .filter(user -> userId.equals(user.getId()))
                .findAny()
                .orElse(null);

        if (userWithId == null) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("user with ID " + userId + " was not found")
                    .asRuntimeException());
            return;
        }

        users.removeIf(user -> userId.equals(user.getId()));

        responseObserver.onNext(DeleteUserResponse.newBuilder()
                .setUser(userWithId)
                .build());

        responseObserver.onCompleted();
    }
}
