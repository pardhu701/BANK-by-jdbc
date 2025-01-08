package bst;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Accounts {
  private Connection conf;
  private Scanner sc;

  Accounts(Connection conf, Scanner sc) {

    this.conf = conf;
    this.sc = sc;
  }

  public void showMenu(String email, String full_name) {
    System.out.println("Welcome " + full_name);
    System.out.println("1. Create Account");
    System.out.println("2. Exiting Account");
    System.out.println("3. Delete Account");
    System.out.println("Enter your choice");
    int choice = sc.nextInt();
    switch (choice) {
      case 1:
        create_account(email, full_name);

        break;
      case 2:
        account_exist(email, full_name);
        break;
      case 3:
      //  delete_account();

        break;
      default:
        System.out.println("Invalid choice");
        break;
    }

  }

  public void create_account(String email, String full_name) {
    System.out.println("CREATING ACCOUNT\n       FILL");
    System.out.println("NAME:" + full_name);
    System.out.println("EMAIL:" + email);
    System.out.print("MIPI ENTER :");
    String mipi = sc.next();
    System.out.println("THANK YOU, Please wait ACCOUNT number");
    try {

      Thread.sleep(1000);
      PreparedStatement ps = conf
          .prepareStatement("insert into accounts(email,full_name, mipi_pin,balance) values(?,?,?,?)");
      ps.setString(1, email);
      ps.setString(2, full_name);
      ps.setString(3, mipi);
      ps.setInt(4, 0);
      int affectedRows = ps.executeUpdate();
      if (affectedRows > 0) {
        System.out.println("Registration Successfull!");
      } else {
        System.out.println("Registration Failed!");
      }
      System.out.println("ACCOUNT CREATED SUCCESSFULLY");
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

  public void account_exist(String email, String full_name) {
    try {
      Statement stmt = conf.createStatement();
      String query = String.format("SELECT * FROM accounts WHERE email='%s'", email);
      ResultSet AE = stmt.executeQuery(query);
      if (AE.next()) {
        System.out.println("Account already exist");
      } else {
        System.out.println("Account does not exist");
        create_account(email, full_name);
      }

    } catch (Exception e) {

    }
  }

}
