package ru.gb.spring_seminar5.repositoreis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.spring_seminar5.models.Executor;

import java.util.List;

/**
 * Репозиторий для сущности executor.
 */
@Repository
public interface ExecutorRepository extends JpaRepository<Executor, Long> {

    /**
     * Получить список задач по статусу
     *
     * @param name имя исполнителя
     * @return список исполнителей
     */
    public List<Executor> findByName(String name);

    /**
     * Получить список исполнителей по возрасту
     *
     * @param age возраст исполнителя
     * @return список исполнителей
     */
    public List<Executor> findByAge(int age);
}

