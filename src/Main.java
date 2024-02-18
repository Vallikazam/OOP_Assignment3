import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Fkbyeh2004";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Assignment_3";

    public static void main(String[] args) throws Exception{
        Scanner cin = new Scanner(System.in);

        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

        while (true) {
            System.out.println("1. Read tasks");
            System.out.println("2. Update task");
            System.out.println("3. Create task");
            System.out.println("4. Delete task");
            System.out.println("5. Exit");

            int input = cin.nextInt();

            if (input == 1){
                Statement statement = connection.createStatement();
                String read_task = "select * from task order by id";
                ResultSet result = statement.executeQuery(read_task);
                while(result.next()){
                    System.out.println(result.getInt("id") + ". "
                            + result.getString("name") + ": "
                            + result.getString("status") + ", "
                            + result.getString("deadline")
                    );
                }
            } else if (input == 2){
                String update_task = "update task set status = 'Done' where id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(update_task);
                System.out.println("Enter task id: ");
                int task_id = cin.nextInt();
                preparedStatement.setInt(1, task_id);
                preparedStatement.executeUpdate();
            } else if (input == 3){
                String create_task = "insert into task(name, status, deadline) values (?, 'In process', ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(create_task);
                System.out.println("Enter task name: ");
                cin.nextLine();
                String task_name = cin.nextLine();
                preparedStatement.setString(1, task_name);

                System.out.println("Enter task deadline (in format - dd/mm/yyyy): ");
                String task_deadline = cin.nextLine();
                preparedStatement.setString(2, task_deadline);
                preparedStatement.executeUpdate();
            } else if (input == 4){
                String delete_task = "delete from task where id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(delete_task);
                System.out.println("Enter task id which you want delete: ");
                int task_id = cin.nextInt();
                preparedStatement.setInt(1, task_id);
                preparedStatement.executeUpdate();
            }else if (input == 5){
                System.exit(0);
            }else {
                System.err.println("Check your input");
            }
        }
    }
}