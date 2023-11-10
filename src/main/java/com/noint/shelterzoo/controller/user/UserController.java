package com.noint.shelterzoo.controller.user;

import com.noint.shelterzoo.service.user.UserService;
import com.noint.shelterzoo.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/sign/up")
    public ResponseEntity<Void> signup(@RequestBody UserDTO.Signup request) {
        userService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/email/check/{email}")
    public ResponseEntity<Boolean> emailDuplicateCheck(@PathVariable String email){
        return new ResponseEntity<>(userService.isExistEmail(email), HttpStatus.OK);
    }

    @GetMapping("/nickname/check/{nickname}")
    public ResponseEntity<Boolean> nicknameDuplicateCheck(@PathVariable String nickname){
        return new ResponseEntity<>(userService.isExistNickname(nickname), HttpStatus.OK);
    }
}