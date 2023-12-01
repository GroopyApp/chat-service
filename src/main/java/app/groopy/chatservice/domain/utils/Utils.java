package app.groopy.chatservice.domain.utils;

public class Utils {

    public static String generateChatName(String topicName) {
        return "chat--" + topicName.toLowerCase().replaceAll(" ", "-");
    }
}
