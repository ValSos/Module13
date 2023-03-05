import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;
import java.util.*;

public class SecondTask {

    public static List<Comment> getCommentsOfUsersLastPost(int userId) throws IOException, InterruptedException, URISyntaxException {
        Optional<Post> lastPostByUser = lastPostUser(userId);
        if (lastPostByUser.isPresent()) {
            return getCommentsPost(userId, lastPostByUser.get().getId());
        }
        return Collections.emptyList();
    }

    private static Optional<Post> lastPostUser(int userId) throws IOException, InterruptedException, URISyntaxException {
        String formattedLink = MessageFormat
                .format("https://jsonplaceholder.typicode.com/users/{0}/posts", userId);
        URI uri = new URI(formattedLink);
        HttpRequest httpRequest = HttpRequest.newBuilder(uri)
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newBuilder().build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        List<Post> postsList = new ArrayList<>();
        if (httpResponse.statusCode() == 200) {
            Type type = TypeToken.getParameterized(List.class, Post.class).getType();
            postsList.addAll(new Gson().fromJson(httpResponse.body(), type));
            return postsList.stream()
                    .max(Comparator.comparingInt(Post::getId));
        }
        return Optional.empty();
    }

    private static List<Comment> getCommentsPost(int userId, int postId) throws IOException, InterruptedException, URISyntaxException {
        String formattedLink = MessageFormat
                .format("https://jsonplaceholder.typicode.com/posts/{0}/comments", postId);
        URI uri = new URI(formattedLink);
        HttpRequest httpRequest = HttpRequest.newBuilder(uri)
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newBuilder().build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        List<Comment> commentsList = new ArrayList<>();
        if (httpResponse.statusCode() == 200) {
            Type type = TypeToken.getParameterized(List.class, Comment.class).getType();
            commentsList.addAll(new Gson().fromJson(httpResponse.body(), type));

            commentsToJsonFile(userId, postId, commentsList);
        }
        return commentsList;
    }

    private static void commentsToJsonFile(int userId, int postId, List<Comment> commentList) {
        //String jsonFile = MessageFormat.format("src/main/java/org/example/user-X-post-Y-comments.json",
                //userId, postId);
        String result = "src/main/java/org/example/user-" + userId + "-post-" + postId + "-comments.json";
        try (Writer fileWriter = new FileWriter(result)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(commentList, fileWriter);
            System.out.println("\nFile has been created in path: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
