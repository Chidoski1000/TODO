package com.company;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class TodoList {
    private ArrayList<Task> taskList;

    public TodoList() {
        taskList = new ArrayList<>();
    }


    public void addTask(String title, String project, LocalDate dueDate) {
        this.taskList.add(new Task(title,project,dueDate));
    }

    public boolean readTaskFromUser() {
        Scanner scan = new Scanner(System.in);

        try {
            System.out.println("Please enter the following details" +
                    " to add a task: title, project and dueDate. Each followed by enter key");
            String title = scan.nextLine();
            String project = scan.nextLine();
            LocalDate dueDate = LocalDate.parse(scan.nextLine());

            this.taskList.add(new Task(title,project,dueDate));
            System.out.println("Task is added successfully");

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }


    public boolean readTaskFromUserToUpdate(Task task) {
        Scanner scan = new Scanner(System.in);
        boolean isTaskUpdated = false;

        try {
            System.out.println("Please enter the following details to update a task:"
                    + "\nIf you do not want to change any field, just press ENTER key!");
            System.out.print(">>> Task Title  : ");
            String title = scan.nextLine();
            if (!(title.trim().equals("") || title == null)) {
                task.setTitle(title);
                isTaskUpdated = true;
            }

            System.out.print(">>> Project Name: ");
            String project = scan.nextLine();
            if (!(project.trim().equals("") || project == null)) {
                task.setProject(project);
                isTaskUpdated = true;
            }

            System.out.print(">>> Due Date [example: 2019-12-31] : ");
            String dueDate = scan.nextLine();
            if (!(dueDate.trim().equals("") || dueDate == null)) {
                task.setDueDate(LocalDate.parse(dueDate));
                isTaskUpdated = true;
            }

            System.out.println("Task is " + (isTaskUpdated ? "updated successfully" : "NOT modified") + ": Returning to Main Menu");

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public void listAllTasksWithIndex() {
        String displayFormat = "%-4s%-35s %-20s %-10s %-10s";

        if (taskList.size()>0) {
            System.out.printf((displayFormat) + "%n","NUM","TITLE","PROJECT","DUE DATE","COMPLETED");
            System.out.printf((displayFormat) + "%n","===","=====","=======","========","=========");
        } else {
            System.out.println("No tasks to show");
        }

        taskList
                .forEach(task -> System.out.printf((displayFormat) + "%n",
                        taskList.indexOf(task)+1,
                        task.getTitle(),
                        task.getProject(),
                        task.getDueDate(),
                        (task.isComplete()?"YES":"NO")
                ));
    }

    public void listAllTasks() {
        String sb = "=".repeat(Math.max(0, 20));
        System.out.println(
                "Total Tasks = " + taskList.size() +
                        "\t\t (Completed = " + completedCount() + "\t\t" +
                        sb + " Not Compeleted = " + notCompletedCount() +
                        " )");

            String displayFormat = "%-20s %-35s %-10s %-10s";

            if (taskList.size()>0) {
                System.out.printf((displayFormat) + "%n","PROJECT","TITLE","DUE DATE","COMPLETED");
                System.out.printf((displayFormat) + "%n","=======","=====","========","=========");
            } else {
                System.out.println("No tasks to show");
            }

            taskList.stream()
                    .sorted(Comparator.comparing(Task::getProject))
                    .forEach(task -> System.out.printf((displayFormat) + "%n",task.getProject(),
                            task.getTitle(),
                            task.getDueDate(),
                            (task.isComplete()?"YES":"NO")
                    ));
    }

    public void editTask(String selectedTask) throws NullPointerException {
        try {

            if (selectedTask.trim().equals("") || selectedTask == null) {
                throw new NullPointerException("EMPTY/NULL TASK NUM: Returning to Main Menu");
            }

            int taskIndex = Integer.parseInt(selectedTask) - 1;
            if (taskIndex < 0 || taskIndex > taskList.size()) {
                throw new ArrayIndexOutOfBoundsException("TASK NUM NOT GIVEN FROM TASK LIST: Returning to Main Menu");
            }

            Task task = taskList.get(taskIndex);

            System.out.println("Task Num " + selectedTask + "  is selected:"                                                                                                                                                                                     );

            Messages.editTaskMenu();
            Scanner scan = new Scanner(System.in);
            String editChoice = scan.nextLine();
            switch (editChoice) {
                case "1":
                    readTaskFromUserToUpdate(task);
                    break;
                case "2":
                    task.markCompleted();
                    Messages.showMessage("Task Num " + selectedTask + " is marked as Completed: Returning to Main Menu", false);
                    break;
                case "3":
                    taskList.remove(task);
                    Messages.showMessage("Task Num " + selectedTask + " is Deleted: Returning to Main Menu", true);
                    break;
                default:
                    Messages.showMessage("Returning to Main Menu", true);
            }
        } catch (Exception e) {
            Messages.showMessage(e.getMessage(),true);
        }
    }

    public int completedCount() {
        return (int) taskList.stream()
                .filter(Task::isComplete)
                .count();
    }

    public int notCompletedCount() {
        return (int) taskList.stream()
                .filter(task -> !task.isComplete())
                .count();
    }

    public boolean readFromFile(String filename) {
        boolean status = false;

        try {
            if (!Files.isReadable(Paths.get(filename))) {
                Messages.showMessage("The data file, i.e., " + filename + " does not exists", true);
                return false;
            }

            FileInputStream fileInputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            taskList = (ArrayList<Task>) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
            return true;

        } catch (Exception e) {
            Messages.showMessage(e.getMessage(),true);
            return false;
        }
    }

    public boolean saveToFile(String filename) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(taskList);

            objectOutputStream.close();
            fileOutputStream.close();
            return true;

        } catch (Exception e) {
            Messages.showMessage(e.getMessage(),true);
            return false;
        }
    }
}
