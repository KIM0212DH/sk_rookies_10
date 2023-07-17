package SuperStrong.coinWallet.entity;

import javax.persistence.*;

@Entity
@Table(name="member_tmp")
public class MemberTmp {
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", nullable = false)
    private String memberId;

    @Column(nullable = false)
    private String pw;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone_num;

//    @Column(nullable = false)
//    private String ssn;

    @Column(nullable = false)
    private String code;



    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phoneNum) {
        this.phone_num = phoneNum;
    }

//    public String getSsn() {
//        return ssn;
//    }
//
//    public void setSsn(String regNum) {
//        this.ssn = regNum;
//    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public void setMember(String memberId, String pw, String email, String phone_num, String code) {
        this.memberId = memberId;
        this.pw = pw;
        this.email = email;
        this.phone_num = phone_num;
//        this.ssn = ssn;
        this.code = code;
    }

    public void setPrivate_key() {
        this.code = code;
    }

}