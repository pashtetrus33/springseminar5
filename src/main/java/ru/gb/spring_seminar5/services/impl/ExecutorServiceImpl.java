package ru.gb.spring_seminar5.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;
import ru.gb.spring_seminar5.aspects.LoggedExecution;
import ru.gb.spring_seminar5.aspects.TrackUserAction;
import ru.gb.spring_seminar5.exceptions.ExecutorNotFoundException;
import ru.gb.spring_seminar5.models.Executor;
import ru.gb.spring_seminar5.repositoreis.ExecutorRepository;
import ru.gb.spring_seminar5.services.ExecutorService;

import java.util.List;


/**
 * Сервис работы с задачами
 */
@Service
@RequiredArgsConstructor
public class ExecutorServiceImpl implements ExecutorService {

    private final ExecutorRepository executorRepository;

    @TrackUserAction(level = Level.INFO)
    @Override
    public List<Executor> getAllExecutors() {
        return executorRepository.findAll();
    }

    @LoggedExecution
    @TrackUserAction(level = Level.INFO)
    @Override
    public Executor getExecutorById(Long id) {
        return executorRepository.findById(id).orElseThrow(() -> new ExecutorNotFoundException("Executor: " + id + " not found!"));
    }


    @Override
    public Executor createExecutor(Executor executor) {
        return executorRepository.save(executor);
    }

    @Override
    public Executor updateExecutor(Long id, Executor executor) {
        if (executorRepository.findById(id).isPresent()) {
            Executor updatedExecutor = executorRepository.findById(id).get();
            updatedExecutor.setName(executor.getName());
            updatedExecutor.setAge(executor.getAge());
            executorRepository.save(updatedExecutor);
            return updatedExecutor;
        }

        throw new ExecutorNotFoundException("Executor with id " + id + " is not found.");
    }

    @LoggedExecution
    @Override
    public void deleteExecutor(Long id) {
        if (executorRepository.findById(id).isPresent()) {
            executorRepository.deleteById(id);
            return;
        }
        throw new ExecutorNotFoundException("Executor with id " + id + " is not found.");
    }

    @Override
    public List<Executor> getExecutorsByAge(int age) {
        return executorRepository.findByAge(age);
    }

    @Override
    public List<Executor> getExecutorsByName(String name) {
        return executorRepository.findByName(name);
    }
}
