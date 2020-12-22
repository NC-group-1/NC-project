package com.nc.project.controller;

import com.nc.project.dto.PasswordDto;
import com.nc.project.exception.UserNotFoundException;
import com.nc.project.model.User;
import com.nc.project.service.mail.EmailService;
import com.nc.project.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class PasswordController {
    private String SUBJECT = "Recovery Password";
    private String PATH_TO_ATTACHMENT = "src/main/resources/email/recovery-password.html";

    private final EmailService emailService;
    private final UserService userService;

    public PasswordController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @PostMapping("/recovery-password")
    public ResponseEntity<?> recoverPassword(@RequestBody String email) {
        User user = userService.findByEmailForRecovery(email).orElseThrow();
        String token = UUID.randomUUID().toString();
        try {
            userService.updateConfirmationToken(user, token);
            String recoverPasswordLink = "http://localhost:4200/password/change?token=" + token;

            emailService.sendMessageWithAttachment(email, SUBJECT, recoverPasswordLink, PATH_TO_ATTACHMENT);
        } catch (UserNotFoundException | MessagingException e) {
;            throw new UserNotFoundException(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/save-password")
    public ResponseEntity<?> savePassword(@RequestBody PasswordDto passwordDto) {
        String token = passwordDto.getToken();
        String result = userService.validatePasswordRecoverToken(token);
        if (result != null) {
            log.error(result);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Integer> user = userService.getUserIdByRecoverPasswordToken(token);
        if (user.isPresent()) {
            userService.changeUserPassword(user.get(), passwordDto.getNewPassword());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
