package com.mycompany.main;

import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);

    static String username;
    static String password;
    static String cellphone;

    static String registeredUsername;
    static String registeredPassword;
    static String registeredCellphone;

    public static boolean checkUserName(String username) {
        return username.length() == 5 && username.contains("_");
    }

    public static boolean checkPasswordComplexity(String password) {
        return password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^a-zA-Z0-9].*");
    }

    public static boolean checkCellphoneNumber(String number) {
        return number.matches("^\\+27[0-9]{9}$");
    }

    public static void registerUser() {
        boolean registrationSuccessful = false;
        while (!registrationSuccessful) {
            System.out.print("Enter your username: ");
            username = input.nextLine();
            System.out.print("Enter your password: ");
            password = input.nextLine();
            System.out.print("Enter your cellphone number (+27): ");
            cellphone = input.nextLine();

            boolean usernameCorrect = checkUserName(username);
            boolean passwordCorrect = checkPasswordComplexity(password);
            boolean phoneCorrect = checkCellphoneNumber(cellphone);

            if (usernameCorrect && passwordCorrect && phoneCorrect) {
                registeredUsername = username;
                registeredPassword = password;
                registeredCellphone = cellphone;
                System.out.println("User registered successfully.");
                registrationSuccessful = true;
            } else {
                System.out.println(
                    "Registration failed. Please ensure:"
                    + "\n- Username contains an underscore and is exactly 5 characters."
                    + "\n- Password is at least 8 characters and contains uppercase, lowercase, number and special character."
                    + "\n- Cellphone number starts with +27 followed by exactly 9 digits."
                );
            }
        }
    }

    public static boolean loginUser(String loginUsername, String loginPassword) {
        return loginUsername.equals(registeredUsername)
                && loginPassword.equals(registeredPassword);
    }

    public static String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + registeredUsername + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    public static void main(String[] args) {

        registerUser();

        System.out.println("\n--- Login ---");
        System.out.print("Enter username to login: ");
        String loginUsernameInput = input.nextLine();
        System.out.print("Enter password to login: ");
        String loginPasswordInput = input.nextLine();

        boolean loginSuccessful = loginUser(loginUsernameInput, loginPasswordInput);
        System.out.println(returnLoginStatus(loginSuccessful));

        if (!loginSuccessful) {
            System.out.println("Login failed. Exiting.");
            input.close();
            return;
        }

        System.out.println("\nWelcome to QuickChat.");

        System.out.print("How many messages do you wish to enter? ");
        int messagesToEnter = Integer.parseInt(input.nextLine());
        int messagesEntered = 0;

        while (true) {
            System.out.println("\n--- QuickChat Menu ---");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose: ");
            int choice = Integer.parseInt(input.nextLine());

            if (choice == 1) {
                if (messagesEntered >= messagesToEnter) {
                    System.out.println("You have entered the maximum number of messages (" + messagesToEnter + ").");
                    continue;
                }

                while (messagesEntered < messagesToEnter) {
                    System.out.print("\nEnter recipient cell number (with international code e.g. +27718693002): ");
                    String recipient = input.nextLine();

                    System.out.print("Enter your message (max 250 chars): ");
                    String messageText = input.nextLine();

                    Messages msgObj = new Messages(messagesEntered, recipient, messageText);

                    if (messageText.length() > 250) {
                        System.out.println("Please enter a message of less than 250 characters.");
                        System.out.println(msgObj.checkMessageLength());
                        continue;
                    } else {
                        System.out.println("Message sent");
                        System.out.println(msgObj.checkMessageLength());
                    }

                    System.out.println(msgObj.checkRecipientCell());

                    System.out.println("\n--- Message Details ---");
                    System.out.println("Message ID: " + msgObj.getMessageID());
                    System.out.println("Message Hash: " + msgObj.getMessageHash());
                    System.out.println("Recipient: " + msgObj.getRecipient());
                    System.out.println("Message: " + msgObj.getMessageText());

                    System.out.println("\nWhat would you like to do?");
                    System.out.println("1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message to send later");
                    System.out.print("Enter option: ");
                    int sendChoice = Integer.parseInt(input.nextLine());

                    String result = msgObj.SentMessage(sendChoice);
                    System.out.println(result);

                    messagesEntered++;

                    if (messagesEntered < messagesToEnter) {
                        System.out.print("Do you want to send another message? (y/n): ");
                        if (!input.nextLine().equalsIgnoreCase("y")) {
                            break;
                        }
                    } else {
                        System.out.println("\nYou have sent all " + messagesToEnter + " messages.");
                        System.out.println("Total messages sent: " + msgObj.returnTotalMessagesess());
                        break;
                    }
                }

            } else if (choice == 2) {
                System.out.println("Coming Soon.");
            } else if (choice == 3) {
                System.out.println("Total messages sent: " + new Messages(0,"","").returnTotalMessagesess());
                System.out.println("Quitting QuickChat.");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }

        input.close();
    }
}