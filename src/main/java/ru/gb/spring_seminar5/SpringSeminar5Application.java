package ru.gb.spring_seminar5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.gb.spring_seminar5.models.PCLTasksChannel;
import ru.gb.spring_seminar5.services.impl.TaskServiceImpl;

/*
Текст заданий в ReadMe.md
 */
@SpringBootApplication
public class SpringSeminar5Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(SpringSeminar5Application.class, args);

        //Реализуйте паттерн Observer для отслеживания изменений в состоянии задач и оповещения об этих изменениях подписчиков.
        TaskServiceImpl taskService = context.getBean(TaskServiceImpl.class);
        taskService.addPropertyChangeListener(new PCLTasksChannel());
    }
}
