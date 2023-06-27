package app.groopy.chatservice.domain.utils;

public class Utils {

    public static String generateChatName(String topicName) {
        return "chat--" + topicName.toLowerCase().replaceAll(" ", "-");
    }

    public static String generateChatGroupName(String topicName, String uuid) {
        return topicName != null && !topicName.isBlank() ?
                "group--" + topicName.toLowerCase().replaceAll(" ", "-") + "-" + uuid :
                "group--main";
    }
}
