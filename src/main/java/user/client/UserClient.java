package user.client;

import com.google.protobuf.FieldMask;
import com.proto.user.*;
import com.proto.user.UserServiceGrpc.UserServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class UserClient {

    static UserServiceBlockingStub client;

    public static void main(String[] args) {

        final ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        client = UserServiceGrpc.newBlockingStub(channel);

        //AddUser RPC unary call
        User user = addUser();

        //ListUsers RPC unary call
        listUsers();

        //GetUser RPC unary call
        getUser(user.getId());

        //UpdateUser RPC unary call
        user = user.toBuilder().setEmail("jonny-d@yahoo.com").build();
        updateUser(user);

        //DeleteUser RPC unary call
        deleteUser(user.getId());

        channel.shutdown();
    }


    private static User addUser() {
        System.out.println("[client-java] making AddUser RPC call");

        AddUserResponse addUserResponse = client.addUser(
                AddUserRequest.newBuilder()
                        .setUser(User.newBuilder()
                                .setFirstName("John")
                                .setEmail("john.doe@gmail.com")
                                .setLatitude(50.424858f)
                                .setLongitude(30.506396f)
                                .build())
                        .build());

        User user = addUserResponse.getUser();
        System.out.printf("[client-java] added user: %s", user);

        return user;
    }

    private static void listUsers() {
        System.out.println("[client-java] making ListUsers RPC call");

        ListUsersResponse listUsersResponse = client.listUsers(
                ListUsersRequest.newBuilder().build());

        System.out.println("Users list:");
        listUsersResponse.getUsersList().forEach(user -> System.out.printf("\t%s\n", user));
    }

    private static User getUser(String userId) {
        System.out.println("[client-java] making GetUser RPC call");

        GetUserResponse getUserResponse = client.getUser(GetUserRequest.newBuilder()
                .setUserId(userId)
                .build());

        final User user = getUserResponse.getUser();

        System.out.println("Found user by id: " + user);
        return user;
    }

    private static User updateUser(User userToUpdate) {
        System.out.println("[client-java] making UpdateUser RPC call");

        UpdateUserResponse updateUserResponse = client.updateUser(UpdateUserRequest.newBuilder()
                .setUser(userToUpdate)
                .setUpdateMask(FieldMask.newBuilder().addPaths("email").build())
                .build());

        User user = updateUserResponse.getUser();
        System.out.println("Updated user: " + user);
        return user;

    }

    private static User deleteUser(String userId) {
        System.out.println("[client-java] making DeleteUser RPC call");

        DeleteUserResponse deleteUserResponse = client.deleteUser(DeleteUserRequest.newBuilder()
                .setUserId(userId)
                .build());

        User user = deleteUserResponse.getUser();
        System.out.println("Deleted user: " + user);

        return user;
    }

}
