package com.company;

public class Messages {

    public static void mainMenu(int incompleteTaskCount, int completedTaskCount) {
        System.out.println("\nMAIN MENU");
        System.out.println(incompleteTaskCount + " task(s) todo "
                + " and " + completedTaskCount + " completed task(s)\n" );
        System.out.println("Pick an option:");
        System.out.println("(1) Show Task List (by date or project)");
        System.out.println("(2) Add New Task");
        System.out.println("(3) Edit Task (update, mark as done, remove)");
        System.out.println("(4) Save and Quit\n");
        System.out.print("Please enter your choice between 1-4: ");
    }

    public static void listAllTasksMenu() {
        System.out.println("\nDisplay All Tasks");
        System.out.println("********************\n");
    }

    public static void editTaskSelection() {
        System.out.print(">>> Type a task number to EDIT and press ENTER key: ");
    }

    public static void editTaskMenu() {
        System.out.println("\nTask Edit Options");
        System.out.println("======================\n");
        System.out.println("Pick an option:");
        System.out.println("(1) Modify selected task");
        System.out.println("(2) Mark selected task as COMPLETED");
        System.out.println("(3) Delete selected task");
        System.out.println("(4) Return to main menu "
                + " [default choice, just press ENTER]");
        System.out.print("\nPlease enter your choice between 1-4: ");
    }

    public static void unknownMessage() {
        System.out.println(">>> Incorrect choice: Please type a number from given choices ");
    }

    public static void showMessage(String message, boolean warning) {
        System.out.println(">>> " + message);
    }

    public static void separator (char charToPrint, int times) {
        for (int index=0; index<times; index++) System.out.print(charToPrint);
        System.out.println("");
    }
}
