import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ThirdTask {
    public static List<Todos> getUncomletedTasks(int userId) throws URISyntaxException, IOException, InterruptedException {
        URI uri = new URI("https://jsonplaceholder.typicode.com/users/"+ userId + "/todos");
        HttpRequest httpRequest = HttpRequest.newBuilder(uri)
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newBuilder().build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        List<Todos> todosList = new ArrayList<>();
        if (httpResponse.statusCode() == 200) {
            Type type = TypeToken.getParameterized(List.class, Todos.class).getType();
            todosList.addAll(new Gson().fromJson(httpResponse.body(), type));
        }
        return todosList.stream()
                .filter(task -> !task.isCompleted())
                .collect(Collectors.toList());
    }

}
