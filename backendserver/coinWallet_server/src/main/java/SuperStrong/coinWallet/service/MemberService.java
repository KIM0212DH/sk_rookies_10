package SuperStrong.coinWallet.service;


import SuperStrong.coinWallet.entity.History;
import SuperStrong.coinWallet.entity.Member;
import SuperStrong.coinWallet.entity.MemberTmp;
import SuperStrong.coinWallet.entity.Wallet;
import SuperStrong.coinWallet.jwttoken.JwtUtil;
import SuperStrong.coinWallet.repository.MemberRepository;
import SuperStrong.coinWallet.repository.MemberTmpRepository;
import SuperStrong.coinWallet.repository.MemberWalletRepository;
import SuperStrong.coinWallet.validation.Encryption;
import io.jsonwebtoken.Jwt;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberTmpRepository memberTmpRepository;
    //    private WalletRepository walletRepository;
    @Autowired
    private MemberWalletRepository memberWalletRepository;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    Encryption encryption;
    public Object loginUser(JSONObject jsonObject) {

        System.out.println(String.format("login_req ===> %s", jsonObject));

        String id = (String) jsonObject.get("id");
        String pw = encryption.sha256((String) jsonObject.get("pw"));

//        System.out.println("id: " + id + " 와 + "  + "pw: " + pw + " 로 로그인 시도");
        Optional<Member> member = memberRepository.findById(id);
        if (member.isPresent() && member.get().getPw().equals(pw)) {
//            System.out.println(member.get().getMemberId());
//            System.out.println("login success");
            String token = jwtUtil.generateToken(member.get().getMemberId(), member.get().getPubAddress());
            HashMap<String, Object> res_map = new HashMap<String, Object>();
//            res_map.put("key",(String) jsonObject.get("key"));
            res_map.put("token", token);

            System.out.println(String.format("login_res ===> %s", res_map));
            return new JSONObject(res_map);
        } else {
            HashMap<String, Object> res_map = new HashMap<String, Object>();
//            res_map.put("key",(String) jsonObject.get("key"));
            res_map.put("token", "login failed");
//            System.out.println("login failed");
            System.out.println(String.format("login_res ===> %s", res_map));
            return new JSONObject(res_map);
        }
    }

    public Object assetInfo(JSONObject jsonObject) {

        System.out.println(String.format("assetInfo_req ===> %s", jsonObject));
        String id = (String) jsonObject.get("id");
//        String key = (String) jsonObject.get("key");
//        Member member = memberRepository.findByPrivateKey(key);
        Member member = memberRepository.findByMemberId(id);
        if (member != null) {
//            System.out.println("id : " + id + " 의 자산 정보를 조회합니다.");
            Wallet memberwallet = member.getWallet();
            HashMap<String, Object> res_map = new HashMap<String, Object>();
            res_map.put("res", "1");
            res_map.put("eth", memberwallet.getEthAmount());
            res_map.put("btc", memberwallet.getBtcAmount());
            res_map.put("doge", memberwallet.getDogeAmount());
            res_map.put("key", (String) jsonObject.get("key"));
            System.out.println(String.format("assetInfo_res ===> %s", res_map));
            return new JSONObject(res_map);
        }
        else {
            HashMap<String, Object> err_res_map = new HashMap<String, Object>();
            err_res_map.put("res", "0");
            err_res_map.put("eth", "");
            err_res_map.put("btc", "");
            err_res_map.put("doge", "");

            err_res_map.put("key", (String) jsonObject.get("key"));
            System.out.println(String.format("assetInfo_res ===> %s", err_res_map));
            return new JSONObject(err_res_map);
        }
    }

    public Object historyInfo(JSONObject jsonObject) {
//        String key = (String) jsonObject.get("key");
        System.out.println(String.format("historyInfo_req ===> %s", jsonObject));

        String id = (String) jsonObject.get("id");
        List<Object> ret_history = new ArrayList<>();
        Member member = memberRepository.findByMemberId(id);
        if (member != null) {
//            System.out.println("id : " + id + " 의 거래 내역을 조회합니다.");
            List<History> histories = member.getHistories();
            ret_history.addAll(histories);
            List<Map<String, Object>> historyList = new ArrayList<>();
            for (Object obj : ret_history) {
                History history = (History) obj;
                if (history.getMember().getMemberId().equals(id)) {
                    Member member2 = memberRepository.findByMemberId(history.getInteractionId());
                    HashMap<String, Object> each_history = new HashMap<String, Object>();
                    each_history.put("history_id", history.getHistoryId());
                    each_history.put("member_id", history.getMember().getMemberId());
                    each_history.put("status", history.getStatus());
                    each_history.put("interaction_id", history.getInteractionId());
                    each_history.put("interaction_address", member2.getPubAddress());
                    each_history.put("coin_name", history.getCoinName());
                    each_history.put("amount", history.getAmount());
                    each_history.put("quote", history.getQuote());
                    each_history.put("gas", history.getGas());
                    each_history.put("date", history.getDate());
                    historyList.add(each_history);
                }
            }
            if (!historyList.isEmpty()) {
                HashMap<String, Object> res_map = new HashMap<String, Object>();
                res_map.put("history", historyList);
                res_map.put("res", "1");
                res_map.put("key", (String) jsonObject.get("key"));
                System.out.println(String.format("historyInfo_res ===> %s", res_map));
                return new JSONObject(res_map);
            }
        }
        HashMap<String, Object> err_res_map = new HashMap<String, Object>();
        err_res_map.put("key", (String) jsonObject.get("key"));
        err_res_map.put("res", "0");
//        err_res_map.put("content", "no history");
        System.out.println(String.format("historyInfo_res ===> %s", err_res_map));
        return new JSONObject(err_res_map);
    }

    public String create_code() {

        Random random = new Random();        //랜덤 함수 선언
        int createNum = 0;              //1자리 난수
        String ranNum = "";             //1자리 난수 형변환 변수
        int letter    = 6;            //난수 자릿수:6
        String resultNum = "";          //결과 난수

        for (int i=0; i<letter; i++) {

            createNum = random.nextInt(9);        //0부터 9까지 올 수 있는 1자리 난수 생성
            ranNum =  Integer.toString(createNum);  //1자리 난수를 String으로 형변환
            resultNum += ranNum;            //생성된 난수(문자열)을 원하는 수(letter)만큼 더하며 나열
        }

//        System.out.println(resultNum);
//        int result = Integer.parseInt(resultNum);
        return resultNum;
    }

    public String create_key(int num) {
        Random rnd =new Random();
        StringBuffer buf =new StringBuffer();
        for(int i=0;i<num;i++){
            // rnd.nextBoolean() 는 랜덤으로 true, false 를 리턴. true일 시 랜덤 한 소문자를, false 일 시 랜덤 한 숫자를 StringBuffer 에 append 한다.
            if(rnd.nextBoolean()){
                buf.append((char)((int)(rnd.nextInt(26))+97));
            }else{
                buf.append((rnd.nextInt(10)));
            }
        }
        String result = buf.toString();
//        System.out.println(result);
        return result;
    }


    public int duplicate_checker(String query){
        try {
            Connection conn = DriverManager.getConnection("url", "username", "password");
            Statement stmt1 = conn.createStatement();

            ResultSet resultSet1 = stmt1.executeQuery(query);
            while (resultSet1.next()) {
                int result = Integer.parseInt( resultSet1.getString("success"));
                if(result==1){
//                    System.out.println("fail");
                    resultSet1.close();
                    stmt1.close();
                    conn.close();
                    return 0;
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    public Object signup(JSONObject jsonObject) {

        // TODO     0:코드발송, 1:id존재, 2:email존재, 3:phone_num존재, 4:ssn존재, 5:이전에 코드 발송
//        JSONObject jsonObject = new JSONObject(data);
        System.out.println(String.format("signup_req ===> %s", jsonObject));
        HashMap<String, String> res = new HashMap<>();

        try {
            if(memberRepository.existsById((String)jsonObject.get("id"))) {
                res.put("res", "1");
                System.out.println(String.format("signup_res ===> %s", res));
                return new JSONObject(res);
            }

            if(memberRepository.existsByEmail((String)jsonObject.get("email"))) {
                res.put("res", "2");
                System.out.println(String.format("signup_res ===> %s", res));
                return new JSONObject(res);
            }

            if(memberRepository.existsByPhoneNum((String)jsonObject.get("phone_num"))) {
                res.put("res", "3");
                System.out.println(String.format("signup_res ===> %s", res));
                return new JSONObject(res);
            }

//            if(memberRepository.existsBySsn((String)jsonObject.get("ssn"))) {
//                res.put("res", "4");
//                System.out.println(String.format("signup_res ===> %s", res));
//                return new JSONObject(res);
//            }

            if(memberTmpRepository.existsById((String)jsonObject.get("id"))) {
                res.put("res", "5");
                System.out.println(String.format("signup_res ===> %s", res));
                return new JSONObject(res);
            }

            String code = create_code();
            mailSender((String) jsonObject.get("email"), code);
            System.out.println(String.format("Code: %s", code));


            MemberTmp tmp = new MemberTmp();
            tmp.setMember((String)jsonObject.get("id"), encryption.sha256((String)jsonObject.get("pw")), (String)jsonObject.get("email"), (String)jsonObject.get("phone_num"), code);
            memberTmpRepository.save(tmp);


            res.put("res", "0");
            System.out.println(String.format("signup_res ===> %s", res));
            return new JSONObject(res);

        } catch (Exception e){
            e.printStackTrace();
        }
//        System.out.println(jsonObject);
        return null;
    }


    public Object signup_auth(JSONObject jsonObject) {
        // TODO 가입 완료 후 member_tmp 테이블에 해당 데이터 삭제 필요

        System.out.println(String.format("signup_auth_req ===> %s", jsonObject));
//        JSONObject jsonObject = new JSONObject(data);
        HashMap<String, String> res = new HashMap<>();
//        res.put("key",(String) jsonObject.get("key"));

        if (memberTmpRepository.existsByCode((String) jsonObject.get("code"))) {
            Optional<MemberTmp> memberTmp = memberTmpRepository.findByCode((String) jsonObject.get("code"));

            String private_key = create_key(32);
            String pub_address = create_key(32);


            Member tmp = new Member();
            tmp.setMember(memberTmp.get().getMemberId(), memberTmp.get().getPw(), memberTmp.get().getEmail(), pub_address, memberTmp.get().getPhone_num(), private_key);
            memberRepository.save(tmp);


            Wallet wallet = new Wallet();
            wallet.setWallet(pub_address, memberTmp.get().getMemberId(), 5, 5, 5);
            memberWalletRepository.save(wallet);


            memberTmp.ifPresent(user -> {
                memberTmpRepository.delete(user);
            });

            res.put("id", memberTmp.get().getMemberId());
            res.put("private_key", private_key);
            res.put("pub_address", pub_address);
            res.put("res", "1");

            System.out.println(String.format("signup_auth_res ===> %s", res));
            JSONObject response = new JSONObject(res);

            return response;
        }
        res.put("id", "");
        res.put("private_key", "");
        res.put("pub_address", "");
        res.put("res", "0");
        System.out.println(String.format("signup_auth_res ===> %s", res));
        return new JSONObject(res);
    }


    @Autowired
    JavaMailSender emailSender;
    public void mailSender(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("superstrong1134@gmail.com");
        message.setTo(email);
        message.setSubject("<<Wallet 인증 코드>>");
        message.setText(String.format("       코드:  %s", code));
        emailSender.send(message);
    }
}
