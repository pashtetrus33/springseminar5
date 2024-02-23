package ru.gb.spring_seminar5.controllers.ui;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.spring_seminar5.enums.TaskType;
import ru.gb.spring_seminar5.factories.CustomTaskFactory;
import ru.gb.spring_seminar5.models.Status;
import ru.gb.spring_seminar5.models.Task;
import ru.gb.spring_seminar5.services.ExecutorService;
import ru.gb.spring_seminar5.services.TaskService;


@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class UiTaskController {

    private final CustomTaskFactory customTaskFactory;
    private final ExecutorService executorService;

    @GetMapping
    public String getAllTasks(Model model) {
        TaskService taskService = customTaskFactory.create(TaskType.NORMAL);
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("executors", executorService.getAllExecutors());
        return "allTasksByCards";
    }


    @GetMapping("save")
    public String saveTask(Model model) {
        model.addAttribute("task", new Task());
        return "saveTask";
    }

    @PostMapping("save")
    public String saveTask(@Valid Task newTask, BindingResult bindingResult, Model model) {
        TaskService taskService;
        if (bindingResult.hasErrors()) {
            taskService = customTaskFactory.create(TaskType.NORMAL);
            model.addAttribute("users", taskService.getAllTasks());
            return "saveTask";
        }

        if (newTask.getTitle().startsWith("Срочно") || newTask.getTitle().startsWith("URGENT")) {
            taskService = customTaskFactory.create(TaskType.URGENT);
        } else {
            taskService = customTaskFactory.create(TaskType.NORMAL);
        }
        taskService.createTask(newTask);
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("executors", executorService.getAllExecutors());
        return "allTasksByCards";
    }

    @GetMapping("/delete/{id}")
    public String deleteTaskById(@PathVariable Long id, Model model) {
        TaskService taskService = customTaskFactory.create(TaskType.NORMAL);
        taskService.deleteTask(id);
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("executors", executorService.getAllExecutors());
        return "allTasksByCards";
    }

    @GetMapping("/upd/{id}")
    public String updateTaskStatus(@PathVariable Long id, Model model) {
        TaskService taskService = customTaskFactory.create(TaskType.NORMAL);
        if (taskService.getTaskById(id).getStatus().equals(Status.URGENT)) {
            taskService = customTaskFactory.create(TaskType.URGENT);
        }
        taskService.updateStatus(id);
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("executors", executorService.getAllExecutors());
        return "allTasksByCards";
    }

    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable("id") Long id, Task task, Model model) {
        TaskService taskService = customTaskFactory.create(TaskType.NORMAL);
        Task updateTask = taskService.updateTask(id, task);
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("executors", executorService.getAllExecutors());
        return "allTasksByCards";
    }

    @GetMapping("update/{id}")
    public String saveTask(@PathVariable("id") Long id, Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("updatedId", id);
        return "updateTask";
    }


    @GetMapping("/status/{status}")
    public String getTasksByStatus(@PathVariable("status") String sts, Model model) {
        TaskService taskService = customTaskFactory.create(TaskType.NORMAL);
        model.addAttribute("tasks", taskService.getTasksByStatus(sts));
        model.addAttribute("executors", executorService.getAllExecutors());
        return "allTasksByCards";
    }

    @GetMapping("/assign/{taskId}/{id}")
    public String assignExecutor(@PathVariable("taskId") Long taskId, @PathVariable("id") Long id, Model model) {
        TaskService taskService = customTaskFactory.create(TaskType.NORMAL);
        taskService.assignExecutor(taskId, id);
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("executors", executorService.getAllExecutors());
        return "allTasksByCards";
    }
}
