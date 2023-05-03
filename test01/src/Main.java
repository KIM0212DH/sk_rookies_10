//import jdk.nashorn.internal.parser.JSONParser;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Random;

public class Main {

    /** ======= [aes128 비밀키 전역 변수 선언] ======= **/
    public static String aes128SecretKey = "01234567890123456789012345678901"; //TODO [aes128 = 16 byte / aes192 = 24 byte / aes256 = 32 byte]
//    public static String aes128SecretKey = "y84us24c2by2yoea8cvdj15p7c8l32v0"; //TODO [aes128 = 16 byte / aes192 = 24 byte / aes256 = 32 byte]
//    public static String aes128SecretKey = "0123456789abcdef0123456789abcdef"; //TODO [aes128 = 16 byte / aes192 = 24 byte / aes256 = 32 byte]
//    public static String aes128SecretKey = "7m8z98e80y93buzw"; //TODO [aes128 = 16 byte / aes192 = 24 byte / aes256 = 32 byte]
//    public static byte[] aes128ivBytes = new byte[16]; // TODO [일반 사용 방식]
    public static byte[] aes128ivBytes = "0123456789012345".getBytes(); // TODO [일반 사용 방식]
    //public static byte[] aes128ivBytes = "0123456789abcdef".getBytes(); // TODO [16 byte Enter IV (Optional) 지정 방식]


    public static void random(){
        Random rnd =new Random();
        StringBuffer buf =new StringBuffer();
        for(int i=0;i<16;i++){
            // rnd.nextBoolean() 는 랜덤으로 true, false 를 리턴. true일 시 랜덤 한 소문자를, false 일 시 랜덤 한 숫자를 StringBuffer 에 append 한다.
            if(rnd.nextBoolean()){
                buf.append((char)((int)(rnd.nextInt(26))+97));
            }else{
                buf.append((rnd.nextInt(10)));
            }
        }
        String result = buf.toString();
        System.out.println(result);
    }

    public static void main(String[] args) {
//        HashMap<String, String> res = new HashMap<>();
//        JSONObject response = new JSONObject(res);

//        System.out.println("Hello world!");

                  /** =======================  JWT decode ===================== **/

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhYWEiLCJzdWIiOiJvamF6cXZ2OWF5ZjJraDE5YzhqN3UzMTJnaHlvYXdpbiIsImlhdCI6MTY4MTEwOTIyNSwiZXhwIjoxNjgxMTEyODI1fQ.p97SAXKwJ560x5-311_LYmrrGIZSKTrwTpZvuXsZYRc";
        String tmp1 = token.substring(token.indexOf(".")+1);
        int tmp2 = tmp1.indexOf(".");
        String result = tmp1.substring(0, tmp2);

        Base64.Decoder decoder = Base64.getUrlDecoder();
        String res = new String(decoder.decode(result));

        System.out.println(res);


                 /** ============================================================== **/
//        System.out.println(result);




//        System.out.println(payload.lastIndexOf(".")+1);
//        System.out.println(payload.indexOf(".")+1);


//        Base64.Decoder decoder = Base64.getUrlDecoder();
//
//        System.out.println(new String(decoder.decode(payload)));




        /** ======= [aes128 인코딩 , 디코딩 선언 방법] ======= **/

//        String aes128EncodeData = getAES128encode("{\"id\":\"aaa\",\"pw\":\"bbb\",\"email\":\"carbigstar@naver.com\",\"phone_num\":\"123123\",\"ssn\":\"123123\"}");
        String aes128EncodeData = getAES128encode("{\"code\":\"855276\"}");
//        String aes128EncodeData = getAES128encode("{\"id\":\"aaa\",\"token\":\"eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhYWEiLCJzdWIiOiJvamF6cXZ2OWF5ZjJraDE5YzhqN3UzMTJnaHlvYXdpbiIsImlhdCI6MTY4MTEwOTIyNSwiZXhwIjoxNjgxMTEyODI1fQ.p97SAXKwJ560x5-311_LYmrrGIZSKTrwTpZvuXsZYRc\",\"pub_address\":\"8otg492s63l30t4zo2e12g28jkwd53td\",\"gas\":\"1.1\",\"coin\":\"btc\",\"amount\":\"0.5\"}");
//        String aes128EncodeData = getAES128encode("{\"id\":\"aaa\",\"pw\":\"bbb\",\"email\":\"carbigstar@naver.com\",\"phone_num\":\"123123\",\"ssn\":\"123123\"}");
//        String aes128EncodeData = getAES128encode("{\"id\":\"aaaaa\",\"pw\":\"bbb\",\"email\":\"carbigstar@naver.com\",\"phone_num\":\"123123123\",\"ssn\":\"1231212123\"}");
//        String aes128DecodeData = getAES128decode(aes128EncodeData);
        String aes128DecodeData = getAES128decode("lX3/UsHsQdP/d4vIfxnQVqVGXXmsaIJyAqp129MDHUbh/NSJF/HIC8v8wUWwjYUb5ZrydObUTZ0ysYJ6YFN8tJejJGHXLqIm8hnQnzqSlDCtHB4WI4QVSAOdqWeWLXWPQZmRSf0VK3DSsl8t1vuWOyLwJHi6R4/3GNVL9yeyMwY=");
//        String aes128DecodeData = getAES128decode("xCZlnpSkuBOus0wxz0HFftaWyk4KxlKPkvxoNzw45ZWXExUTW1vmB+MAWEIMT+9h3GLDltFOvEiod5Z0+KhVhISoGvDfSq9/T42BFuFH5QMtCrILkAwCodmNelW2iq0M7Mr0kvAWIB8t1z4Fvv+whk4NUXYjgTiXN+/9XUewTgb1uoxajOIYprHxc2wJvrBr+31M1r8Ya1yGk9TxIzyCkQ==");
        System.out.println(aes128EncodeData);
        System.out.println(aes128DecodeData);
//        random();
    }



    /** ======= [aes128 비밀키 사용해 인코딩 수행] ======= **/
    public static String getAES128encode (String encodeData){
        String result = "";
        try {
            System.out.println("\n");
            System.out.println("=======================================");
            System.out.println("[ModuleApiController] : [getAES128encode] : [aes128 비밀키 사용해 인코딩 수행 실시]");
            System.out.println("[secretKey] : " + String.valueOf(aes128SecretKey));
            System.out.println("[encodeData] : " + String.valueOf(encodeData));
            System.out.println("=======================================");
            System.out.println("\n");

            byte[] textBytes = encodeData.getBytes("UTF-8");
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(aes128ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(aes128SecretKey.getBytes("UTF-8"), "AES");
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);

            System.out.println("\n");
            System.out.println("=======================================");
            System.out.println("[ModuleApiController] : [getAES128encode] : [aes128 비밀키 사용해 인코딩 수행 결과]");
            System.out.println("[secretKey] : " + String.valueOf(aes128SecretKey));
            System.out.println("[결과] : " + String.valueOf(Base64.getEncoder().encodeToString(cipher.doFinal(textBytes))));
            System.out.println("=======================================");
            System.out.println("\n");

            // TODO [리턴 데이터 반환]
            return Base64.getEncoder().encodeToString(cipher.doFinal(textBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /** ======= [aes128 비밀키 사용해 디코딩 수행] ======= **/
    public static String getAES128decode (String decodeData){
        String result = "";
        try {
            System.out.println("\n");
            System.out.println("=======================================");
            System.out.println("[ModuleApiController] : [getAES128decode] : [aes128 비밀키 사용해 디코딩 수행 실시]");
            System.out.println("[secretKey] : " + String.valueOf(aes128SecretKey));
            System.out.println("[decodeData] : " + String.valueOf(decodeData));
            System.out.println("=======================================");
            System.out.println("\n");

            // TODO [인풋으로 들어온 base64 문자열 데이터를 가지고 디코딩 수행]
            byte[] textBytes = Base64.getDecoder().decode(decodeData.getBytes());
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(aes128ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(aes128SecretKey.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);

            System.out.println("\n");
            System.out.println("=======================================");
            System.out.println("[ModuleApiController] : [getAES128decode] : [aes128 비밀키 사용해 디코딩 수행 결과]");
            System.out.println("[secretKey] : " + String.valueOf(aes128SecretKey));
            System.out.println("[결과] : " + String.valueOf(new String(cipher.doFinal(textBytes), "UTF-8")));
            System.out.println("=======================================");
            System.out.println("\n");

            // TODO [리턴 데이터 반환]
            return new String(cipher.doFinal(textBytes), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



}