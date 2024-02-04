package ru.gb.spring_seminar5.repositoreis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.spring_seminar5.models.Status;
import ru.gb.spring_seminar5.models.Task;

import java.util.List;

/**
 * Репозиторий для сущности task.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Получить список задач по статусу
     *
     * @param status статус задачи
     * @return список задач
     */
    public List<Task> findByStatus(Status status);
}

