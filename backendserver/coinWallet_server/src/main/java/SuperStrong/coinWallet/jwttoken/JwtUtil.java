package SuperStrong.coinWallet.jwttoken;

import SuperStrong.coinWallet.entity.Member;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import SuperStrong.coinWallet.repository.MemberRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
public class JwtUtil {
    private static final String SECRET_KEY = "mysecretkeymysecretkeymysecretkeymysecretkeymysecretkey";


    @Autowired
    private MemberRepository memberRepository;
    public String generateToken(String id, String pub_address) { //id 파라미터는 데이터베이스에서 id 정보를 받아온다.
        String combinedKey = SECRET_KEY + pub_address;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        return Jwts.builder()
                .setId(id)
                .setSubject(pub_address)
                .setIssuedAt(now)
                .setExpiration(new Date(nowMillis + 10000000))
                .signWith(SignatureAlgorithm.HS256, combinedKey)
                .compact();
    }

    public boolean CheckTokenValid(JSONObject jsonObject, String token) {
        String id = (String) jsonObject.get("id");
        Member member = memberRepository.findByMemberId(id);
        String pub_address = member.getPubAddress();
        try {
            String combinedKey = SECRET_KEY + pub_address;
            Jwts.parser().setSigningKey(combinedKey).parseClaimsJws(token);
            return true;
        }
        catch (ExpiredJwtException e) {
            // Handle expired token exception
            return false;
        }
        catch (JwtException e) {
            // Handle invalid token exception
            return false;
        }
//        return false;
    }
    public Object tokenNoValidReturn(JSONObject jsonObject) {
        System.out.println("**********token exp*************");
        HashMap<String, Object> res_map = new HashMap<String, Object>();
        res_map.put("key", (String) jsonObject.get("key"));
        res_map.put("res", "2");
        return new JSONObject(res_map);
    }


    public String getId(HashMap<String, Object> data) throws ParseException {

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

        String res_id = (String) json.get("jti");

        return res_id;
    }


    public String getPrivateKey(String id) {
        Optional<Member> member = memberRepository.findById(id);
        String key = member.get().getPrivateKey();

        return key;
    }
}
