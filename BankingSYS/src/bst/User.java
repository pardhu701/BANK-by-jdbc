package bst;

import java.sql.*;
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
            System.out.println("Pin for recovery");
            System.out.print("enter your pin: ");
            String pin = sc.nextLine();

            // checjing password match or not
            if (password.equals(cppassword)) {
                boolean flag = user_exist(email);
                System.out.println(flag);
                // checking if the user exist or not
                if (flag == true) {
                    PreparedStatement ps = conf
                            .prepareStatement("insert into userdetails(email,passwd,full_name, pin) values(?,?,?,?)");
                    ps.setString(1, email);
                    ps.setString(2, password);
                    ps.setString(3, full_name);
                    ps.setString(4, pin);
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

    public String login() {
        try {
            int flag = 0;
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
                        return email;
                    } else {
                        flag++;
                        // System.out.println(flag);
                        System.out.println("password incorrect");
                        if (flag == 3) {
                            ForgotPassword(email);
                            flag = 0;
                        }

                    }

                } else {
                    System.out.println("email not exist");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
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

    public String reset() {
        System.out.println("enter new password");
        String ret = sc.next();
        return ret;
    }

    public void ForgotPassword(String email) {

        try {
            System.out.println("enter pin to reset password");
            int pin = sc.nextInt();
            Statement stmt = conf.createStatement();
            String check_detail = String.format("select * from userdetails  where email='%s' AND pin='%d'", email, pin);
            ResultSet rs = stmt.executeQuery(check_detail);
            if (rs.next()) {
                String newPassword = reset();
                String updatePassword = String.format("UPDATE userdetails SET passwd='%s' WHERE email='%s'", newPassword, email);
                stmt.executeUpdate(updatePassword);
                System.out.println("Password has been successfully reset.");
            }
            else{
                System.out.println("Incorrect PIN. Please try again.");
                int attempts = 1;
                while (attempts < 3) {
                    System.out.println("Enter pin to reset password:");
                    pin = sc.nextInt();
                    rs = stmt.executeQuery(String.format("SELECT * FROM userdetails WHERE email='%s' AND pin='%d'", email, pin));
                    if (rs.next()) {
                        // Valid PIN, prompt for a new password
                        String newPassword = reset();
                        String updatePassword = String.format("UPDATE userdetails SET passwd='%s' WHERE email='%s'", newPassword, email);
                        stmt.executeUpdate(updatePassword);
                        System.out.println("Password has been successfully reset.");
                        return;
                    }
                    attempts++;
                    System.out.println("Incorrect PIN. You have " + (3 - attempts) + " attempts left.");
                }
                System.out.println("Too many failed attempts. Please try again later.");
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}