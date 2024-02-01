package ru.gb.spring_seminar5.controllers.ui;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring_seminar5.models.Task;
import ru.gb.spring_seminar5.services.ExecutorService;
import ru.gb.spring_seminar5.services.TaskService;


@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class UiTaskController {

    private final TaskService taskService;
    private final ExecutorService executorService;

    @GetMapping
    public String getAllTasks(Model model) {
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
    public String saveUser(@Valid Task task, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("users", taskService.getAllTasks());
            return "saveTask";
        }
        taskService.createTask(task);
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("executors", executorService.getAllExecutors());
        return "allTasksByCards";
    }

    @GetMapping("/delete/{id}")
    public String deleteTaskById(@PathVariable Long id, Model model) {
        taskService.deleteTask(id);
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("executors", executorService.getAllExecutors());
        return "allTasksByCards";
    }

    @GetMapping("/upd/{id}")
    public String updateTaskStatus(@PathVariable Long id, Model model) {
        taskService.updateStatus(id);
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("executors", executorService.getAllExecutors());
        return "allTasksByCards";
    }

    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable("id") Long id, Task task, Model model) {
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
        model.addAttribute("tasks", taskService.getTasksByStatus(sts));
        model.addAttribute("executors", executorService.getAllExecutors());
        return "allTasksByCards";
    }

    @GetMapping("/assign/{taskId}/{id}")
    public String assignExecutor(@PathVariable("taskId") Long taskId, @PathVariable("id") Long id, Model model) {
        taskService.assignExecutor(taskId, id);
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("executors", executorService.getAllExecutors());
        return "allTasksByCards";
    }
}
