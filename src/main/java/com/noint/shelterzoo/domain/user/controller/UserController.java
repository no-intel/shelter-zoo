package com.noint.shelterzoo.domain.user.controller;

import com.noint.shelterzoo.domain.user.dto.req.LoginRequestDTO;
import com.noint.shelterzoo.domain.user.dto.req.SignupRequestDTO;
import com.noint.shelterzoo.domain.user.dto.res.MyInfoResponseDTO;
import com.noint.shelterzoo.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final HttpSession session;

    @PostMapping("/sign/up")
    public ResponseEntity<Void> signup(@RequestBody SignupRequestDTO request) {
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

    @PostMapping("/login")
    public ResponseEntity<MyInfoResponseDTO> login(@RequestBody LoginRequestDTO request) {
        MyInfoResponseDTO myInfo = userService.login(request);
        session.setAttribute("seq", myInfo.getSeq());
        session.setAttribute("email", myInfo.getEmail());
        session.setAttribute("nickname", myInfo.getNickname());
        session.setMaxInactiveInterval(60*30);

        return new ResponseEntity<>(myInfo, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<MyInfoResponseDTO> myInfo() {
        String email = (String) session.getAttribute("email");
        return new ResponseEntity<>(userService.myInfo(email), HttpStatus.OK);
    }

    @PatchMapping("/resign")
    public ResponseEntity<Void>resign(){
        Long seq = (Long) session.getAttribute("seq");
        userService.resign(seq);
        session.invalidate();
        return ResponseEntity.noContent().build();
    }
}
