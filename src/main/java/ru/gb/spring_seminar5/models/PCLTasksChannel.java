package ru.gb.spring_seminar5.models;

import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@Slf4j
public class PCLTasksChannel implements PropertyChangeListener {

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        log.info("Изменение свойства: {} старое значение {} новое значение {}",evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
    }
}
