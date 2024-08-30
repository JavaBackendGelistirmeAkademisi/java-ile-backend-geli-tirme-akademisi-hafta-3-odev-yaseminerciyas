import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {

        User yasemin = new User("Yasemin");
        User defne = new User("Defne");

        yasemin.follow(defne);
        defne.createPost("Hello World!");
        yasemin.addCommentToPost(defne, 0, "Nice post!");
        yasemin.addToPostFavorites(defne, 0);

        yasemin.showFeed();
        defne.showPosts();
    }

    static class Post implements Comparable<Post> {
        private int id;
        private User user;
        private String content;
        private LinkedList<Comment> comments; // Post yorumları
        private static int commentCounter = 0;

        public Post(int id, User user, String content) {
            this.id = id;
            this.user = user;
            this.content = content;
            this.comments = new LinkedList<>();
        }

        public int getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public User getUser() {
            return user;
        }

        public void addComment(Comment comment) {
            comments.add(comment);
        }

        public void showComments() {
            for (Comment comment : comments) {
                System.out.println("  - " + comment.getText() + " (by " + comment.getUser().getName() + ")");
            }
        }

        @Override
        public int compareTo(Post other) {
            return Integer.compare(this.id, other.id);
        }
    }

    static class Comment {
        private User user;
        private String text;

        public Comment(User user, String text) {
            this.user = user;
            this.text = text;
        }

        public User getUser() {
            return user;
        }

        public String getText() {
            return text;
        }
    }

    static class User {
        private String name;
        private LinkedHashMap<Integer, Post> posts;
        private Set<User> following;
        private TreeSet<Post> favorites;
        private static int postCounter = 0;

        public User(String name) {
            this.name = name;
            this.posts = new LinkedHashMap<>();
            this.following = new HashSet<>();
            this.favorites = new TreeSet<>();
        }

        public String getName() {
            return name;
        }

        public void follow(User user) {
            following.add(user);
            System.out.println(name + " kullanıcısını " + user.getName() + " takip ediyor");
        }

        public void createPost(String content) {
            Post newPost = new Post(postCounter++, this, content);
            posts.put(newPost.getId(), newPost);
            System.out.println(name + " yeni bir gönderi yayınladı: " + content);
        }

        public void addCommentToPost(User user, int postId, String comment) {
            Post post = user.getPost(postId);
            if (post != null) {
                post.addComment(new Comment(this, comment));
                System.out.println(name + " yorum yaptı: " + comment);
            }
        }

        public void addToPostFavorites(User user, int postId) {
            Post post = user.getPost(postId);
            if (post != null) {
                favorites.add(post);
                System.out.println(name + " gönderisini beğendi: " + post.getContent());
            }
        }

        public void showFeed() {
            System.out.println("\n" + name + "'in Ana Sayfası");
            for (User user : following) {
                user.showPosts();
            }
        }

        public void showPosts() {
            System.out.println(name + "'in Gönderileri:");
            for (Post post : posts.values()) {
                System.out.println(" - " + post.getContent());
                post.showComments();
            }
        }

        public Post getPost(int postId) {
            return posts.get(postId);
        }
    }
}