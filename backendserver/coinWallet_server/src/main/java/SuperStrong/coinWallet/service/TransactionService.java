package SuperStrong.coinWallet.service;

import SuperStrong.coinWallet.controller.HomeController;
import SuperStrong.coinWallet.entity.Member;
import SuperStrong.coinWallet.entity.Wallet;
import SuperStrong.coinWallet.repository.MemberRepository;
import SuperStrong.coinWallet.repository.MemberWalletRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TransactionService {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Autowired
    private MemberRepository memberRepository;

    public Object calculate(JSONObject jsonObject) {
        Logger log = LogManager.getLogger(HomeController.class);

        System.out.println(String.format("calculate_req ===> %s", jsonObject));
        Double iRate = 0.05;
        HashMap<String, Object> res_map = new HashMap<String, Object>();
        res_map.put("key",(String) jsonObject.get("key"));
        String id = (String) jsonObject.get("id");
        String coin_name = (String) jsonObject.get("coin_name");
        Double send_amount = (Double) Double.valueOf(jsonObject.get("send_amount").toString());
        Optional<Member> optionalMember = memberRepository.findById(id);

        String to_address = (String) jsonObject.get("to_address");

        log.debug(to_address);

        if (!(memberRepository.existsByPubAddress(to_address))) {
            HashMap<String, Object> err_res_map = new HashMap<String, Object>();
            err_res_map.put("key",(String) jsonObject.get("key"));
            err_res_map.put("res", "0");
            err_res_map.put("content", "no destination");
            System.out.println(String.format("calculate_res ===> %s", err_res_map));
            return new JSONObject(err_res_map);
        }

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            Wallet memberwallet = member.getWallet();
            Double calculated_gas = 0.0;
            if (coin_name.equals("eth") || coin_name.equals("ETH")) {
                Double remain_amount = memberwallet.getEthAmount();
                calculated_gas = Math.ceil(send_amount * iRate * 100) / 100;
                if (remain_amount - send_amount - calculated_gas < 0) {
                    //보유 자산 부족 에러
                    HashMap<String, Object> err_res_map = new HashMap<String, Object>();
                    err_res_map.put("res", "0");
                    err_res_map.put("content", "not enough assets");
                    err_res_map.put("key",(String) jsonObject.get("key"));
                    System.out.println(String.format("calculate_res ===> %s", err_res_map));
                    return new JSONObject(err_res_map);
                }
                remain_amount = remain_amount - send_amount - calculated_gas;
                res_map.put("remain_amount", remain_amount);
            }
            else if (coin_name.equals("btc") || coin_name.equals("BTC")) {
                Double remain_amount = memberwallet.getBtcAmount();
                calculated_gas = Math.ceil(send_amount * iRate * 100) / 100;
                if (remain_amount - send_amount - calculated_gas < 0) {
                    //보유 자산 부족 에러
                    HashMap<String, Object> err_res_map = new HashMap<String, Object>();
                    err_res_map.put("res", "0");
                    err_res_map.put("content", "not enough assets");
                    err_res_map.put("key",(String) jsonObject.get("key"));
                    System.out.println(String.format("calculate_res ===> %s", err_res_map));
                    return new JSONObject(err_res_map);
                }
                remain_amount = remain_amount - send_amount - calculated_gas;
                res_map.put("remain_amount", remain_amount);
            }
            else {
                Double remain_amount = memberwallet.getDogeAmount();
                calculated_gas = Math.ceil(send_amount * iRate * 100) / 100;
                if (remain_amount - send_amount - calculated_gas < 0) {
                    //보유 자산 부족 에러
                    HashMap<String, Object> err_res_map = new HashMap<String, Object>();
                    err_res_map.put("res", "0");
                    err_res_map.put("content", "not enough assets");
                    err_res_map.put("key",(String) jsonObject.get("key"));
                    System.out.println(String.format("calculate_res ===> %s", err_res_map));
                    return new JSONObject(err_res_map);
                }
                remain_amount = remain_amount - send_amount - calculated_gas;
                res_map.put("remain_amount", remain_amount);
            }
            res_map.put("calculated_gas", calculated_gas);
            res_map.put("send_amount", send_amount);
            res_map.put("coin_name", coin_name);
            res_map.put("to_address", to_address);
            res_map.put("res", "1");
//            System.out.println(id + " 가 " + to_address + " 지갑 주소로 " + coin_name + " 을 " + send_amount + " 만큼 전송. 가스는 " + calculated_gas);
        }
        System.out.println(String.format("calculate_res ===> %s", res_map));
        return new JSONObject(res_map);
    }


    public Object send(JSONObject jsonObject) {
        System.out.println(String.format("send_req ===> %s", jsonObject));
        try {

//            System.out.println(jsonObject);
//            JSONObject jsonObject = new JSONObject(data);
            HashMap<String, String> res = new HashMap<>();
            res.put("key",(String) jsonObject.get("key"));

            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt1 = conn.createStatement();

            if (Objects.equals(jsonObject.get("coin_name"), "ETH") || Objects.equals(jsonObject.get("coin_name"), "BTC") || Objects.equals(jsonObject.get("coin_name"), "DOGE")) {
                double coin = (double) jsonObject.get("amount");

//                double coin = Double.parseDouble((String) jsonObject.get("amount"));
                double gas = (double) jsonObject.get("gas");
//                double gas = Double.parseDouble((String) jsonObject.get("gas"));

                String query1 = String.format("UPDATE member_wallet SET %s_amount = %s_amount - %f WHERE member_id = '%s';", jsonObject.get("coin_name"), jsonObject.get("coin_name"), coin, jsonObject.get("id"));
                stmt1.executeUpdate(query1);

                String query2 = String.format("UPDATE server_wallet SET %s_amount = %s_amount + %f;", jsonObject.get("coin_name"), jsonObject.get("coin_name"), gas);
                stmt1.executeUpdate(query2);

                String query3 = String.format("UPDATE member_wallet SET %s_amount = %s_amount + %f WHERE wallet_id = '%s';", jsonObject.get("coin_name"), jsonObject.get("coin_name"), coin, jsonObject.get("pub_address"));
                stmt1.executeUpdate(query3);

                // TODO quote 변동 추가
                double quote = 1500000;
                String status = "send";

                Member member = memberRepository.findByPubAddress((String) jsonObject.get("pub_address"));
                String interaction_id = member.getMemberId();

                Date now = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formatedNow = formatter.format(now);


                String query4 = String.format("INSERT INTO history (status, member_id, interaction_id, coin_name, amount, quote, gas, date) VALUES ('%s', '%s', '%s', '%s', '%f', '%f', '%f', '%s');", status, jsonObject.get("id"), interaction_id, jsonObject.get("coin_name"), coin, quote, gas, formatedNow);
                stmt1.executeUpdate(query4);

                status = "receive";

                String query5 = String.format("INSERT INTO history (status, member_id, interaction_id, coin_name, amount, quote, gas, date) VALUES ('%s', '%s', '%s', '%s', '%f', '%f', '%f', '%s');", status, interaction_id, jsonObject.get("id"), jsonObject.get("coin_name"), coin, quote, gas, formatedNow);
                stmt1.executeUpdate(query5);

                stmt1.close();
                conn.close();

                res.put("res", "1");
                System.out.println(String.format("send_res ===> %s", res));
                return new JSONObject(res);
            } else {
                stmt1.close();
                conn.close();

                res.put("res", "0");
                System.out.println(String.format("send_res ===> %s", res));
                return new JSONObject(res);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}