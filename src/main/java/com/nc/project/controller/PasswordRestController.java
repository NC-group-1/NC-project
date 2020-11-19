package com.nc.project.controller;

import com.nc.project.dto.PasswordDto;
import com.nc.project.exception.UserNotFoundException;
import com.nc.project.model.Email;
import com.nc.project.model.RecoveryToken;
import com.nc.project.model.User;
import com.nc.project.service.EmailService;
import com.nc.project.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("") //add request mapping
@CrossOrigin(origins = "http://localhost:4200")
public class PasswordRestController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;

    @PostMapping("/recovery-password")
    public ResponseEntity<?> recoverPassword(@RequestBody Email email) throws UnsupportedOperationException {
        User user = userService.findByEmail(email.getRecipients().get(0));
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        String token = UUID.randomUUID().toString();
        try {
            userService.updateConfirmationToken(user, token);
            String recoverPasswordLink = "http://localhost:4200/password/change?token=" + token;
            emailService.sendMessageWithAttachment(email, recoverPasswordLink);
        } catch (UserNotFoundException | MessagingException e) {
            System.out.println("User not found" + e.getMessage());
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
        Optional<User> user = userService.getUserByRecoverPasswordToken(token);
        if (user.isPresent()) {
            RecoveryToken recoveryToken = userService.getRecoverTokenByToken(token);
            userService.changeUserPassword(recoveryToken, passwordDto.getNewPassword());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
