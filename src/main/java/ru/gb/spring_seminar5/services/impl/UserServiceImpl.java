package ru.gb.spring_seminar5.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gb.spring_seminar5.constants.Constants;
import ru.gb.spring_seminar5.models.User;
import ru.gb.spring_seminar5.repositoreis.UserRepository;
import ru.gb.spring_seminar5.security.CustomUserDetailsService;
import ru.gb.spring_seminar5.security.jwt.JwtUtil;
import ru.gb.spring_seminar5.services.UserService;
import ru.gb.spring_seminar5.utils.Utils;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("SignUp for map: {}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                User user = userRepository.findByEmail(requestMap.get(EMAIL));
                if (Objects.isNull(user)) {
                    userRepository.save(getUserFromMap(requestMap));
                    return Utils.getResponseEntity("User has been registered", HttpStatus.CREATED);
                } else {
                    return Utils.getResponseEntity("Email is already registered", HttpStatus.BAD_REQUEST);
                }
            } else {
                return Utils.getResponseEntity(Constants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Utils.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Login method is called: " + requestMap);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get(EMAIL), requestMap.get(PASSWORD))
            );
            if (authentication.isAuthenticated()) {
                if (customUserDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<>("{\"access_token\":\"" + jwtUtil.generateToken(customUserDetailsService.getUserDetails().getEmail(),
                            customUserDetailsService.getUserDetails().getRole()) + "\"}", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("{\"message\":\"" + "Waiting for admin approve" + "\"}", HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception exception) {
            log.error("Error: " + exception);
        }
        return new ResponseEntity<>("{\"message\":\"" + "Incorrect credentials " + "\"}", HttpStatus.BAD_REQUEST);
    }

    private boolean validateSignUpMap(Map<String, String> requestMap) {
        return (requestMap.containsKey("name") && requestMap.containsKey(EMAIL) && requestMap.containsKey(PASSWORD));
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setEmail(requestMap.get(EMAIL));
        user.setPassword(requestMap.get(PASSWORD));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }
}
