package qbfd;

public class LibraryDiscussionForum {
    public void startDiscussion(User user, String topic, String message) {
        System.out.println(user.getUsername() + " started a discussion on '" + topic + "': " + message);
    }
}