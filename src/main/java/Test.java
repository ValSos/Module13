import java.io.IOException;
import java.net.URISyntaxException;

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        System.out.println("__________FirstTaskTest__________");
        User user = new User();
        user.setId(11);
        user.setPhone("0505050555");
        user.setEmail("lera.dovgan.96@gmail.com");
        user.setName("Valeriia");
        user.setUsername("Sosiedka");

        FirstTask.getUserById(1);
        FirstTask.getAllUsers();
        FirstTask.deleteUser(1);
        FirstTask.getUserByUsername("Antonette");

        System.out.println("__________SecondTaskTest__________");
        System.out.println(SecondTask.getCommentsOfUsersLastPost(1));

        System.out.println("__________ThirdTaskTest__________");
        System.out.println(ThirdTask.getUncomletedTasks(1));
    }
}
