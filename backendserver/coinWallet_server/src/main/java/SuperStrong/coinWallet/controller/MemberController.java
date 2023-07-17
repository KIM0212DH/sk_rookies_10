package SuperStrong.coinWallet.controller;


import SuperStrong.coinWallet.jwttoken.JwtUtil;
import SuperStrong.coinWallet.service.MemberService;
import SuperStrong.coinWallet.validation.Encryption;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberController {

    @Autowired
    MemberService memberService;
    @Autowired
    Encryption encryption;



    @PostMapping("/api/register")
    public Object register(@RequestBody HashMap<String, Object> data) throws ParseException {
        return encryption.getAES256encode2(memberService.signup(encryption.getAES256decode2(data)));
    }


    @PostMapping("/api/register/auth")
    public Object register_auth(@RequestBody HashMap<String, Object> data) throws ParseException {
        return encryption.getAES256encode2(memberService.signup_auth(encryption.getAES256decode2(data)));
    }


    @PostMapping("/api/login")
    public Object login(@RequestBody HashMap<String, Object> data) throws ParseException {
        return encryption.getAES256encode2(memberService.loginUser(encryption.getAES256decode2(data)));
    }
}
