package com.company;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task implements Serializable {
    private String tastTitle;
    private String projectTitle;
    private boolean completeStatus;
    private LocalDate dueDate;

    public Task(String tastTitle, String projectTitle, LocalDate dueDate) {
        this.setTastTitle(tastTitle);
        this.setProjectTitle(projectTitle);
        this.completeStatus = false;
        this.setDueDate(dueDate);
    }

    public String getTastTitle() {
        return this.tastTitle;
    }

    public void setTastTitle(String tastTitle) throws NullPointerException {
        if (tastTitle.trim().equals("") || tastTitle == null) {
            throw new NullPointerException("REQUIRED: Title can not be empty.");
        }
        this.tastTitle = tastTitle.trim();
    }

    public String getProjectTitle() {
        return this.projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle.trim();
    }

    public boolean isCompleteStatus() {
        return this.completeStatus;
    }

    public boolean markInComplete() {
        this.completeStatus = false;
        return this.completeStatus;
    }

    public boolean markCompleted() {
        this.completeStatus = true;
        return this.completeStatus;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) throws DateTimeException {
        if (dueDate.compareTo(LocalDate.now())<0) {
            throw new DateTimeException("Past Date not allowed");
        }

        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.dueDate = LocalDate.parse(dueDate.format(formattedDate));
    }

    public String formattedStringOfTask() {
        return (
                "\nTitle     : " + tastTitle +
                        "\nProject   : " + projectTitle +
                        "\nStatus    : " + (completeStatus ?"Completed":"NOT COMPLETED") +
                        "\nDue Date  : " + dueDate +
                        "\n");
    }
}
