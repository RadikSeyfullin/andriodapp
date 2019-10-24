package com.example.todolist;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Todo {
    public String text;
    public boolean isCompleted;
    public int projectRef;

    public Todo(String text_t, boolean isCompleted_t, int projectRef_t){
        text = text_t;
        isCompleted = isCompleted_t;
        projectRef = projectRef_t;
    }
}
