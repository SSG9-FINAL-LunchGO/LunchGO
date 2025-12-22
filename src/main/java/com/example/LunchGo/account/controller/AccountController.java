package com.example.LunchGo.account.controller;

import com.example.LunchGo.account.dto.OwnerFindRequest;
import com.example.LunchGo.account.dto.OwnerJoinRequest;
import com.example.LunchGo.account.dto.UserFindRequest;
import com.example.LunchGo.account.dto.UserJoinRequest;
import com.example.LunchGo.account.helper.AccountHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api")
public class AccountController {
    private final AccountHelper accountHelper;

    @PostMapping("/join/user")
    public ResponseEntity<?> userJoin(@RequestBody UserJoinRequest userReq) {
        if(!StringUtils.hasLength(userReq.getEmail()) || !StringUtils.hasLength(userReq.getPassword()) || !StringUtils.hasLength(userReq.getName()) ||
           !StringUtils.hasLength(userReq.getPhone()) || !StringUtils.hasLength(userReq.getCompanyName()) || !StringUtils.hasLength(userReq.getCompanyAddress())) {
            //이메일 수신 동의는 필수 아님
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        accountHelper.userJoin(userReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/auth/email")
    public ResponseEntity<?> checkEmail(@RequestBody UserJoinRequest userReq) {
        if(!StringUtils.hasLength(userReq.getEmail())) { //이메일 검증이니 이메일만 체크
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        accountHelper.checkEmail(userReq.getEmail()); //존재하면 여기서 409 에러 던짐
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/join/owner")
    public ResponseEntity<?> ownerJoin(@RequestBody OwnerJoinRequest ownerReq) {
        if(!StringUtils.hasLength(ownerReq.getLoginId()) || !StringUtils.hasLength(ownerReq.getPassword()) || !StringUtils.hasLength(ownerReq.getName()) ||
            !StringUtils.hasLength(ownerReq.getPhone()) || !StringUtils.hasLength(ownerReq.getBusinessNum())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        accountHelper.ownerJoin(ownerReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/auth/loginId")
    public ResponseEntity<?> checkLoginId(@RequestBody OwnerJoinRequest ownerReq) {
        if(!StringUtils.hasLength(ownerReq.getLoginId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        accountHelper.checkLoginId(ownerReq.getLoginId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/auth/search/email")
    public ResponseEntity<?> searchEmail(@RequestBody UserFindRequest userReq) {
        if(!StringUtils.hasLength(userReq.getName()) || !StringUtils.hasLength(userReq.getPhone()) || !StringUtils.hasLength(userReq.getVerifyCode())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String findEmail = accountHelper.getEmail(userReq);

        Map<String, String> response = Collections.singletonMap("email", findEmail); //찾은 이메일 객체에 담아 보내기

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/auth/search/loginId")
    public ResponseEntity<?> searchLoginId(@RequestBody OwnerFindRequest ownerReq) {
        if(!StringUtils.hasLength(ownerReq.getName()) || !StringUtils.hasLength(ownerReq.getBusinessNum()) || !StringUtils.hasLength(ownerReq.getPhone()) ||
                !StringUtils.hasLength(ownerReq.getVerifyCode())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String findLoginId = accountHelper.getLoginId(ownerReq);

        Map<String, String> response = Collections.singletonMap("loginId", findLoginId); //찾은 아이디 객체에 담아 보내기

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
