package SuperStrong.coinWallet.controller;


import SuperStrong.coinWallet.entity.History;
import SuperStrong.coinWallet.jwttoken.JwtUtil;
import SuperStrong.coinWallet.service.MemberService;
import SuperStrong.coinWallet.validation.Encryption;
import io.jsonwebtoken.Jwt;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Base64;
import java.nio.charset.StandardCharsets;

@RestController
public class HomeController {

    Logger log = LogManager.getLogger(HomeController.class);

    @Autowired
    MemberService memberService;
    @Autowired
    Encryption encryption;
    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/api/main_asset") // id와 jwt 받아옴, jwt에 대한 유효성 검사 intercept 필요함
    public Object main_asset(@RequestBody HashMap<String, Object> data) throws ParseException {
        if (jwtUtil.CheckTokenValid(encryption.getAES256decode(data), (String) data.get("token"))) {
            return encryption.getAES256encode(memberService.assetInfo(encryption.getAES256decode(data)));
        }
        return encryption.getAES256encode(jwtUtil.tokenNoValidReturn(encryption.getAES256decode(data)));
    }

    @PostMapping("/api/main_history")
    public Object main_history(@RequestBody HashMap<String, Object> data) throws ParseException {
        if (jwtUtil.CheckTokenValid(encryption.getAES256decode(data), (String) data.get("token"))) {
            return encryption.getAES256encode(memberService.historyInfo(encryption.getAES256decode(data)));
        }
        return encryption.getAES256encode(jwtUtil.tokenNoValidReturn(encryption.getAES256decode(data)));
    }



    @RequestMapping("/")
    public String index(String name) {
        System.out.println(encryption.sha256("aaa"));
        System.out.println(encryption.sha256("bbb"));
        System.out.println(encryption.sha256("asjdklfajlsdf"));
        log.debug("index! name:{}", name);
        return "index";
    }
}
