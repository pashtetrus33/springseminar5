package ru.gb.spring_seminar5.services;

import org.slf4j.event.Level;
import org.springframework.http.ResponseEntity;
import ru.gb.spring_seminar5.aspects.TrackUserAction;

import java.util.Map;

@TrackUserAction(level = Level.WARN)
public interface UserService {
    ResponseEntity<String> signUp(Map<String, String> requestMap);

    ResponseEntity<String> login(Map<String, String> requestMap);
}
