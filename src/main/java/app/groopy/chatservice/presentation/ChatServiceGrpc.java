package app.groopy.chatservice.presentation;

import app.groopy.protobuf.ChatServiceProto;
import app.groopy.chatservice.application.ApplicationService;
import app.groopy.chatservice.application.exceptions.ApplicationException;
import app.groopy.chatservice.presentation.mapper.PresentationMapper;
import app.groopy.chatservice.presentation.resolver.ApplicationExceptionResolver;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ChatServiceGrpc extends app.groopy.protobuf.ChatServiceGrpc.ChatServiceImplBase {

    private final Logger LOGGER = LoggerFactory.getLogger(ChatServiceGrpc.class);

    private final ApplicationService applicationService;
    private final PresentationMapper presentationMapper;

    @Autowired
    public ChatServiceGrpc(ApplicationService applicationService, PresentationMapper presentationMapper) {
        this.applicationService = applicationService;
        this.presentationMapper = presentationMapper;
    }

    @Override
    public void createChannel(ChatServiceProto.CreateChatRoomRequest request, StreamObserver<ChatServiceProto.CreateChatRoomResponse> responseObserver) {
        LOGGER.info("Processing CreateChatRoomRequest {}", request);
        try {
            var chatRoom = applicationService.createChatRoom(presentationMapper.map(request));
            responseObserver.onNext(ChatServiceProto.CreateChatRoomResponse.newBuilder()
                            .setChannelName(chatRoom.getChannelName())
                            .setGroupName(chatRoom.getGroupName())
                            .setUuid(chatRoom.getUuid())
                    .build());
            responseObserver.onCompleted();
        } catch (ApplicationException e) {
            responseObserver.onError(ApplicationExceptionResolver.resolve(e));
        }
    }
}