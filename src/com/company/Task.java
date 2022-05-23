package com.company;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task implements Serializable {
    private String title;
    private String project;
    private boolean complete;
    private LocalDate dueDate;

    public Task(String title, String project, LocalDate dueDate) {
        this.setTitle(title);
        this.setProject(project);
        this.complete = false;
        this.setDueDate(dueDate);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) throws NullPointerException {
        if (title.trim().equals("") || title == null) {
            throw new NullPointerException("REQUIRED: Title can not be empty.");
        }
        this.title = title.trim();
    }

    public String getProject() {
        return this.project;
    }

    public void setProject(String project) {
        this.project = project.trim();
    }

    public boolean isComplete() {
        return this.complete;
    }

    public boolean markInComplete() {
        this.complete = false;
        return this.complete;
    }

    public boolean markCompleted() {
        this.complete = true;
        return this.complete;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) throws DateTimeException {
        // Throw DateTimeException if past date is given
        if (dueDate.compareTo(LocalDate.now())<0) {
            throw new DateTimeException("Past Date not allowed");
        }

        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.dueDate = LocalDate.parse(dueDate.format(formattedDate));
    }

    public String formattedStringOfTask() {
        return (
                "\nTitle     : " + title +
                        "\nProject   : " + project +
                        "\nStatus    : " + (complete?"Completed":"NOT COMPLETED") +
                        "\nDue Date  : " + dueDate +
                        "\n");
    }
}
