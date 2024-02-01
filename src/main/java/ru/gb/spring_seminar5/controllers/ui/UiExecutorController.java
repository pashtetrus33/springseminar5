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
import ru.gb.spring_seminar5.models.Executor;
import ru.gb.spring_seminar5.services.ExecutorService;;

@Controller
@RequestMapping("/executors")
@RequiredArgsConstructor
public class UiExecutorController {

    private final ExecutorService executorService;

    @GetMapping
    public String getAllTasks(Model model) {
        model.addAttribute("executors", executorService.getAllExecutors());
        return "allExecutors";
    }


    @GetMapping("save")
    public String saveTask(Model model) {
        model.addAttribute("executor", new Executor());
        return "saveExecutor";
    }

    @PostMapping("save")
    public String saveExecutor(@Valid Executor executor, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("executors", executorService.getAllExecutors());
            return "saveExecutor";
        }
        executorService.createExecutor(executor);
        model.addAttribute("executors", executorService.getAllExecutors());
        return "allExecutors";
    }

    @GetMapping("/delete/{id}")
    public String deleteExecutorById(@PathVariable Long id, Model model) {
        executorService.deleteExecutor(id);
        model.addAttribute("executors", executorService.getAllExecutors());
        return "allExecutors";
    }

    @PostMapping("/update/{id}")
    public String updateExecutor(@PathVariable("id") Long id, @Valid Executor executor, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("executors", executorService.getAllExecutors());
            return "updateExecutor";
        }
        Executor updateExecutor = executorService.updateExecutor(id, executor);
        model.addAttribute("executors", executorService.getAllExecutors());
        return "allExecutors";
    }

    @GetMapping("update/{id}")
    public String saveExecutor(@PathVariable("id") Long id, Model model) {
        model.addAttribute("executor", new Executor());
        model.addAttribute("updatedId", id);
        return "updateExecutor";
    }
}
