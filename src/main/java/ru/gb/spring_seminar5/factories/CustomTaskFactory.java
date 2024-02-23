package ru.gb.spring_seminar5.factories;

import ru.gb.spring_seminar5.enums.TaskType;
import ru.gb.spring_seminar5.services.TaskService;

public interface CustomTaskFactory {
    TaskService create(TaskType taskType);
}
