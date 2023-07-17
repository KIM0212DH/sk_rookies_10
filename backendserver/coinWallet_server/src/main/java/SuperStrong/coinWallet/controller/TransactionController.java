package SuperStrong.coinWallet.controller;


import SuperStrong.coinWallet.jwttoken.JwtUtil;
import SuperStrong.coinWallet.repository.MemberRepository;
import SuperStrong.coinWallet.service.MemberService;
import SuperStrong.coinWallet.service.TransactionService;
import SuperStrong.coinWallet.validation.Encryption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class TransactionController {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    Encryption encryption;
    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/api/send")
    public Object send(@RequestBody HashMap<String, Object> data) throws ParseException {
        JSONObject obj = encryption.getAES256decode(data);
        if (Objects.equals(jwtUtil.getId(data), (String) obj.get("id"))) {
            return encryption.getAES256encode(transactionService.send(encryption.getAES256decode(data)));
        }
        return encryption.getAES256encode(jwtUtil.tokenNoValidReturn(encryption.getAES256decode(data)));
    }

    @PostMapping("/api/send_input") //토큰 유효성 검사, 인터셉트 필요******
    public Object sendInput(@RequestBody HashMap<String, Object> data) throws ParseException {
        if (jwtUtil.CheckTokenValid(encryption.getAES256decode(data), (String) data.get("token"))) {
            return encryption.getAES256encode(transactionService.calculate(encryption.getAES256decode(data)));
        }
        return encryption.getAES256encode(jwtUtil.tokenNoValidReturn(encryption.getAES256decode(data)));
    }
}