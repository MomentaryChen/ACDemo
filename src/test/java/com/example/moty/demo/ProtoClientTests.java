package com.example.moty.demo;

import com.example.moty.demo.model.protobuf.UserProto;
import com.example.moty.demo.model.protobuf.UserServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ProtoClientTests {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 18081)
                .usePlaintext()
                .build();

        UserServiceGrpc.UserServiceBlockingStub stub = UserServiceGrpc.newBlockingStub(channel);

        UserProto.User user = UserProto.User.newBuilder()
                .setId(1)
                .setName("Alice")
                .setEmail("alice@example.com")
                .build();

        UserProto.UserResponse response = stub.createUser(user);
        System.out.println("RPC 回傳: " + response.getMessage());

        channel.shutdown();
    }
    
}
