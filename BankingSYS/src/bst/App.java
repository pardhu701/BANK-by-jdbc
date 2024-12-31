package bst;

import java.sql.*;
import java.util.*;


public class App {
    private static final String url = "jdbc:mysql://localhost:3306/banksystem";
    private static final String username = "root";
    private static final String password = "pardhu701";

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection conf = DriverManager.getConnection(url, username, password);
           //onfig config = new Config(conf);
            User user = new User(conf, sc);
            while (true) {
                System.out.println("Welcome to Banking App");
                System.out.println("1.register\n2.Login\n3.Exit");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        user.register();
                        break;
                    case 2:
                        user.login();   

                    case 3:
                        System.out.println("thank you for using our app");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice");
                        break;

                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
