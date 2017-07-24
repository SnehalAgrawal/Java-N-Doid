package com.example.admin.restapi.ListAdapter;

/**
 * Created by Admin on 7/2/2017.
 */

public class TaskCard {
    String taskid, task, taskcreated, taskstatus;

    public TaskCard(String taskid, String task, String taskcreated, String taskstatus) {
        this.taskid = taskid;
        this.task = task;
        this.taskcreated = taskcreated;
        this.taskstatus = taskstatus;
    }
}