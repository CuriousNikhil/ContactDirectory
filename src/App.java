import directory.Directory;
import jdk.nashorn.internal.AssertsEnabled;
import test.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Queue;
import java.util.Random;

public class App {

    public static void main(String[] args) {

        Directory<String> tst = new Directory<>();
        App app = new App();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("############### Contacts Directory ###############");

        if (args.length > 0) {
            if (args[0].equals("test")) {
                //Test the application
                AssertsEnabled.assertsEnabled();
                Test test = new Test();
                test.runTests();
                // All tests passed
            }
        } else {
            //Run the application
            app.run(br, tst);
        }

    }

    //main program to run
    private void run(BufferedReader br, Directory<String> tst) {
        while (true) {
            System.out.println("Please choose your option:");
            System.out.println("1] Add contact\t 2] Search contact\t 3] Exit  :");
            try {
                int choice = Integer.parseInt(br.readLine());

                switch (choice) {
                    case 1:
                        addContact(tst, br);
                        break;
                    case 2:
                        searchContact(tst, br);
                        break;
                    case 3:
                        System.out.println("Happy Searching");
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.exit(0);
                    default:
                        System.out.println("Please enter correct option:");
                        System.out.println();
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println("Please enter correct choice!");
                System.out.println();
            }

        }
    }

    //add contact name in directory
    //Storing a random number as a value with contact name/ can use values like list of details etc
    private void addContact(Directory<String> tst, BufferedReader s) {
        System.out.println("Enter name: ");
        try {
            String contact = s.readLine().trim();
            if (contact.matches("[A-Za-z\\s]+$")) {
                int number = new Random(10).nextInt();
                tst.add(contact, String.valueOf(number));
                System.out.println("Contact added");
            } else {
                System.out.println("Contact name should not contain other characters");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    //search contact name in directory
    private void searchContact(Directory<String> tst, BufferedReader s) {
        System.out.println("Enter name: ");
        try {
            String query = s.readLine();
            Queue<String> contacts = tst.searchContact(query);
//            System.out.println(tst.get(contacts.peek()));
            displayContacts(contacts);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Display the result of directory
    private void displayContacts(Queue<String> result) {
        System.out.println("Results from contact directory:");
        for (String x : result) {
            System.out.println(x);
        }
        System.out.println();
    }
}
