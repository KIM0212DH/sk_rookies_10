package SuperStrong.coinWallet.entity;

import javax.persistence.*;

@Entity
@Table(name = "member_wallet")
public class Wallet {
    @Id
    @Column(name = "wallet_id")
    private String walletId;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "eth_amount")
    private double ethAmount;

    @Column(name = "btc_amount")
    private double btcAmount;

    @Column(name = "doge_amount")
    private double dogeAmount;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Member member;
    public Wallet() {}

    public Wallet(String walletId, double ethAmount, double btcAmount, double dogeAmount, Member member) {
        this.walletId = walletId;
        this.ethAmount = ethAmount;
        this.btcAmount = btcAmount;
        this.dogeAmount = dogeAmount;
        this.member = member;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public double getEthAmount() {
        return ethAmount;
    }

    public void setEthAmount(int ethAmount) {
        this.ethAmount = ethAmount;
    }

    public double getBtcAmount() {
        return btcAmount;
    }

    public void setBtcAmount(int btcAmount) {
        this.btcAmount = btcAmount;
    }

    public double getDogeAmount() {
        return dogeAmount;
    }

    public void setDogeAmount(int dogeAmount) {
        this.dogeAmount = dogeAmount;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }




    public void setWallet(String walletId, String memberId, double ethAmount, double btcAmount, double dogeAmount) {
        this.walletId = walletId;
        this.memberId = memberId;
        this.ethAmount = ethAmount;
        this.btcAmount = btcAmount;
        this.dogeAmount = dogeAmount;
    }



}
