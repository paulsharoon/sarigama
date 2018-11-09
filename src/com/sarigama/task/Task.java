package com.sarigama.task;

import com.sarigama.task.db.tables.TaskDetails;

public interface Task {
    
    public void execute(TaskDetails taskDetails);
    
}