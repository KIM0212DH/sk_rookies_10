package SuperStrong.coinWallet.validation;

import SuperStrong.coinWallet.entity.Member;
import SuperStrong.coinWallet.repository.MemberRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import java.util.Base64;
import java.nio.charset.StandardCharsets;

@Service
public class Encryption {
    /** ======= [aes256 비밀키 전역 변수 선언] ======= **/
    public static String aes256SecretKey = "01234567890123456789012345678901"; //TODO [aes128 = 16 byte / aes192 = 24 byte / aes256 = 32 byte]
    //    public static String aes128SecretKey = "7m8z98e80y93buzw"; //TODO [aes128 = 16 byte / aes192 = 24 byte / aes256 = 32 byte]
//    public static byte[] aes256ivBytes = new byte[16]; // TODO [일반 사용 방식]
    public static byte[] aes256ivBytes = "0123456789012345".getBytes(); // TODO [16 byte Enter IV (Optional) 지정 방식]


    @Autowired
    private MemberRepository memberRepository;

    /** ======= [aes256 비밀키 사용해 인코딩 수행] ======= **/
    public Object getAES256encode (Object data){
        System.out.println("\n==============================================================================================================================================================================================\n");
        JSONObject jsonObject = new JSONObject((Map) data);
//        System.out.println(String.format("************: %s", jsonObject.get("key")));

        String key = (String) jsonObject.get("key");
//        System.out.println(key);

        jsonObject.remove("key");
        String res = jsonObject.toString();

//        String res = String.valueOf(data);
        HashMap<String, String> result = new HashMap<>();

        try {
            byte[] textBytes = res.getBytes("UTF-8");
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(aes256ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);

            String result2 = Base64.getEncoder().encodeToString(cipher.doFinal(textBytes));
            result.put("e2e_res", result2);
//            System.out.println(result);

            return new JSONObject(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /** ======= [aes256 비밀키 사용해 디코딩 수행] ======= **/
    public JSONObject getAES256decode (HashMap<String, Object> data) throws ParseException {

        JSONObject jsonObject = new JSONObject(data);
        String req = ((String) jsonObject.get("e2e_req")).replace("\n", "");
//        String req = ((String) jsonObject.get("e2e_req")).replace("\n", "");

//        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhYWEiLCJzdWIiOiJvamF6cXZ2OWF5ZjJraDE5YzhqN3UzMTJnaHlvYXdpbiIsImlhdCI6MTY4MTEwOTIyNSwiZXhwIjoxNjgxMTEyODI1fQ.p97SAXKwJ560x5-311_LYmrrGIZSKTrwTpZvuXsZYRc";
        String token = ((String) jsonObject.get("token")).replace("\n", "");
//        String token = ((String) jsonObject.get("token")).replace("\n", "");
        String tmp1 = token.substring(token.indexOf(".")+1);
        int tmp2 = tmp1.indexOf(".");
        String result = tmp1.substring(0, tmp2);

        Base64.Decoder decoder = Base64.getUrlDecoder();
        String res = new String(decoder.decode(result));

//        System.out.println(res);

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(res);
        JSONObject json = (JSONObject) obj;

//        System.out.println(json.get("jti"));

        Optional<Member> member = memberRepository.findById((String) json.get("jti"));
        String key = member.get().getPrivateKey();
//        System.out.println(key);

        try {
            // TODO [인풋으로 들어온 base64 문자열 데이터를 가지고 디코딩 수행]
            byte[] textBytes = Base64.getDecoder().decode(req.getBytes());
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(aes256ivBytes);
//            SecretKeySpec newKey = new SecretKeySpec(aes256SecretKey.getBytes("UTF-8"), "AES");
            SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);

            String result2 = new String(cipher.doFinal(textBytes), "UTF-8");
            JSONParser parser2 = new JSONParser();
            Object obj2 = parser2.parse(result2);
            JSONObject json2 = (JSONObject) obj2;
            json2.put("key", key);

//            System.out.println(json2);
            return json2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Object getAES256encode2 (Object data){
        System.out.println("\n==============================================================================================================================================================================================\n");
//        String res = String.valueOf(data);
        HashMap<String, String> result = new HashMap<>();

        JSONObject jsonObject = new JSONObject((Map) data);
        jsonObject.remove("key");
        String res = jsonObject.toString();

        try {
            byte[] textBytes = res.getBytes("UTF-8");
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(aes256ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(aes256SecretKey.getBytes("UTF-8"), "AES");
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);

            String result2 = Base64.getEncoder().encodeToString(cipher.doFinal(textBytes));
            result.put("e2e_res", result2);
//            System.out.println(result);

            return new JSONObject(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /** ======= [aes256 비밀키 사용해 디코딩 수행] ======= **/
    public JSONObject getAES256decode2 (HashMap<String, Object> data) throws ParseException {

        JSONObject jsonObject = new JSONObject(data);
        String req = ((String) jsonObject.get("e2e_req")).replace("\n", "");

        try {
            // TODO [인풋으로 들어온 base64 문자열 데이터를 가지고 디코딩 수행]
            byte[] textBytes = Base64.getDecoder().decode(req.getBytes());
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(aes256ivBytes);
//            SecretKeySpec newKey = new SecretKeySpec(aes256SecretKey.getBytes("UTF-8"), "AES");
            SecretKeySpec newKey = new SecretKeySpec(aes256SecretKey.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);

            String result2 = new String(cipher.doFinal(textBytes), "UTF-8");
            JSONParser parser2 = new JSONParser();
            Object obj2 = parser2.parse(result2);
            JSONObject json2 = (JSONObject) obj2;
            return json2;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String sha256 (String text) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }




}
