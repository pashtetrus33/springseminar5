package ru.gb.spring_seminar5.controllers.ui;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring_seminar5.models.Task;
import ru.gb.spring_seminar5.models.dto.TaskDto;
import ru.gb.spring_seminar5.services.TaskService;

import java.util.List;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class UiTaskController {

    private final TaskService taskService;

    @GetMapping
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
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
        return "allTasksByCards";
    }

    @GetMapping("/delete/{id}")
    public String deleteTaskById(@PathVariable Long id, Model model) {
        taskService.deleteTask(id);
        model.addAttribute("tasks", taskService.getAllTasks());
        return "allTasksByCards";
    }

    @GetMapping("/upd/{id}")
    public String updateTaskStatus(@PathVariable Long id, Model model) {
        taskService.updateStatus(id);
        model.addAttribute("tasks", taskService.getAllTasks());
        return "allTasksByCards";
    }

    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable("id") Long id, Task task, Model model) {
        Task updateTask = taskService.updateTask(id, task);
        model.addAttribute("tasks", taskService.getAllTasks());
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
        return "allTasksByCards";
    }
}
