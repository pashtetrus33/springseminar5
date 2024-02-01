package ru.gb.spring_seminar5.exceptions;

/**
 * Исключение при отсутствии исполнителя.
 */
public class ExecutorNotFoundException extends RuntimeException {
    /**
     * Конструктор родительского класса.
     *
     * @param message сообщение об ошибке
     */
    public ExecutorNotFoundException(String message) {
        super(message);
    }
}
