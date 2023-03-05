import com.google.gson.Gson;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public abstract class FirstTask {
    private static final String USERS_URL =
            "https://jsonplaceholder.typicode.com/users";
    private static final Gson GSON = new Gson();
    private static final HttpClient CLIENT = HttpClient.newHttpClient();


    public static User createNewObject(User user) throws URISyntaxException, IOException, InterruptedException {
        final String requestBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(USERS_URL))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response = CLIENT.send(request,HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }


    public static void updateObject(int id, User user) throws Exception {
        String body = GSON.toJson(user);
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request = (HttpRequest) HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/" + id))
                .PUT(HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        System.out.println("Update status: " + response.statusCode());
    }

    public static void deleteUser(int id) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request = (HttpRequest) HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/" + id))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        System.out.println("Update status: " + response.statusCode());


    }

    public static User getUserById(int id) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request = (HttpRequest) HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/" + id))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        System.out.println("Update status: " + response.statusCode());
        final User user = GSON.fromJson(response.body(), User.class);
        System.out.println(user);
        return user;
    }
//https://jsonplaceholder.typicode.com/users?username=
    public static String getUserByUsername(String username) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request = (HttpRequest) HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users" + "?username=" + username))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        System.out.println("Update status: " + response.statusCode());
        //final User user = GSON.fromJson(response.body(), User.class); //почему не раюотает по аналогии с гэт по ид, не возвращает объект юзера?
        System.out.println(response.body());
        return response.body();
    }



    public static String getAllUsers() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request = (HttpRequest) HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/users/"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        System.out.println("Update status: " + response.statusCode());
        String result = response.body().toString();
        System.out.println(result);
        return result;
    }

}

