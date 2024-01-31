package ru.gb.spring_seminar5.exceptions;

/**
 * Исключение при отсутствии статуса.
 */
public class StatusNotFoundException extends RuntimeException {
    /**
     * Конструктор родительского класса.
     *
     * @param message сообщение об ошибке
     */
    public StatusNotFoundException(String message) {
        super(message);
    }
}
