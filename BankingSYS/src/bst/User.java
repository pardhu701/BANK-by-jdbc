package bst;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
    private Connection conf;
    private Scanner sc;

    // connection from the app
    User(Connection conf, Scanner sc) {
        this.conf = conf;
        this.sc = sc;
    }

    // user registertion
    public void register() throws SQLException {
        while (true) {
            System.out.println("-----registeration form-----");
            sc.nextLine();
            System.out.print("enter your full_name: ");
            String full_name = sc.nextLine();
            System.out.print("enter your email_id: ");
            String email = sc.nextLine();
            System.out.print("enter your password: ");
            String password = sc.nextLine();
            System.out.print("confirm your password: ");
            String cppassword = sc.nextLine();
            // checjing password match or not
            if (password.equals(cppassword)) {
                boolean flag = user_exist(email);
                System.out.println(flag);
                // checking if the user exist or not
                if (flag == true) {
                    PreparedStatement ps = conf
                            .prepareStatement("insert into userdetails(email,passwd,full_name) values(?,?,?)");
                    ps.setString(1, email);
                    ps.setString(2, password);
                    ps.setString(3, full_name);
                    int affectedRows = ps.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Registration Successfull!");
                    } else {
                        System.out.println("Registration Failed!");
                    }
                    return;
                } else {
                    System.out.println("email already exist");
                }

            }

            else {
                System.out.println("password mismatch");

            }
        }

    }

    public void login() {
        try {
            while (true) {
                System.out.println("----login form----");
                sc.nextLine();
                System.out.print("enter your email_id: ");
                String email = sc.nextLine();
                System.out.print("enter your password: ");
                String password = sc.nextLine();
                Statement stmt = conf.createStatement();
                String check_email = String.format("Select * from userdetails where email='%s'", email);
                ResultSet rs_log = stmt.executeQuery(check_email);
                if (rs_log.next()) {
                    String check_password = String.format("Select * from userdetails where passwd='%s'", password);
                    ResultSet rs_pass = stmt.executeQuery(check_password);
                    if (rs_pass.next()) {
                        System.out.println("login successfull");
                        return;}
                    else {
                        System.out.println("password incorrect");
                    }

                } else {
                    System.out.println("email not exist");
                }
            }
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean user_exist(String emailid) {

        try {
            Statement stmt = conf.createStatement();
            String query = String.format("Select * from userdetails where email='%s'", emailid);
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next())
                return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return true;

    }

}
