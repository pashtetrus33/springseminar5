package ru.gb.spring_seminar5.services.impl;

import org.springframework.stereotype.Component;
import ru.gb.spring_seminar5.enums.TaskType;
import ru.gb.spring_seminar5.exceptions.TaskTypeNotFoundException;
import ru.gb.spring_seminar5.factories.CustomTaskFactory;
import ru.gb.spring_seminar5.services.TaskService;

import java.text.MessageFormat;
import java.util.List;

@Component
public class CustomTaskFactoryImpl implements CustomTaskFactory {


    private final List<TaskService> concreteTaskServices;

    public CustomTaskFactoryImpl(List<TaskService> concreteTaskServices) {
        this.concreteTaskServices = concreteTaskServices;
    }

    @Override
    public TaskService create(TaskType taskType) {
        switch (taskType) {
            case NORMAL -> {
                return getTask(TaskServiceImpl.class);
            }
            case URGENT -> {
                return getTask(UrgentTaskServiceImpl.class);
            }

            default -> throw new TaskTypeNotFoundException(MessageFormat.format(
                    "{0} is not currently supported as a payment method.", taskType
            ));
        }
    }

    private TaskService getTask(Class type) {
        return (TaskService) concreteTaskServices.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .findFirst()
                .orElse(null);
    }
}
