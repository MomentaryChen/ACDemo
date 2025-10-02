package com.example.moty.demo.service.proto;

import com.example.moty.demo.model.protobuf.UserProto;
import com.example.moty.demo.model.protobuf.UserServiceGrpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService 
public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {

    @Override
    public void createUser(UserProto.User request, StreamObserver<UserProto.UserResponse> responseObserver) {
        System.out.println("收到 RPC: " + request.getName());

        UserProto.UserResponse response = UserProto.UserResponse.newBuilder()
                .setSuccess(true)
                .setMessage("User created: " + request.getName())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}