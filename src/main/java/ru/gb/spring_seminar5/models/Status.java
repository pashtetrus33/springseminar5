package ru.gb.spring_seminar5.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Статусы задач.
 * Не начата, в процессе выполнения, выполнена.
 */

public enum Status {

    COMPLETED("Завершена"),
    IN_PROGRESS("В процессе"),
    NOT_STARTED("Не начата");

    private String title;

    Status(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}