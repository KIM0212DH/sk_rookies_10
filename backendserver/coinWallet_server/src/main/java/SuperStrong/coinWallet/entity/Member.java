package SuperStrong.coinWallet.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @Column(name = "id", nullable = false)
    private String memberId;

    @Column(nullable = false)
    private String pw;

    @Column(nullable = false)
    private String email;

    @Column(name = "pub_address", nullable = false)
    private String pubAddress;

    @Column(name = "phone_num", nullable = false)
    private String phoneNum;

//    @Column(name = "ssn", nullable = false)
//    private String ssn;

    @Column(name = "private_key", nullable = false)
    private String privateKey;


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
    public String getPubAddress() {
        return pubAddress;
    }
    public void setPubAddress(String pubAddress) {
        this.pubAddress = pubAddress;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
//    public String getSsn() {
//        return ssn;
//    }
//    public void setSsn(String ssn) {
//        this.ssn = ssn;
//    }
    public String getPrivateKey() {
        return privateKey;
    }
    public void setPrivateKey(String private_key) {
        this.privateKey = private_key;
    }


    public void setMember(String memberId, String pw, String email, String pubAddress, String phoneNum, String privateKey) {
        this.memberId = memberId;
        this.pw = pw;
        this.email = email;
        this.pubAddress = pubAddress;
        this.phoneNum = phoneNum;
//        this.ssn = ssn;
        this.privateKey = privateKey;
    }

    @OneToOne(mappedBy = "member")
    private Wallet wallet;
    public Wallet getWallet() {
        return wallet;
    }
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
        wallet.setMember(this);
    }


    @OneToMany(mappedBy = "member")
    private List<History> histories;
    public List<History> getHistories() {
        return histories;
    }
    public void setHistories(List<History> histories) {
        this.histories = histories;
    }
}
