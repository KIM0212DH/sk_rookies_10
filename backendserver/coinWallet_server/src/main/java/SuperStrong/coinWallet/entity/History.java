package SuperStrong.coinWallet.entity;

import javax.persistence.*;

@Entity
@Table(name = "history")
public class History {
    @Id
    @Column(name = "history_id")
    private int historyId;

    @Column(name = "status")
    private String status;

    @Column(name = "interaction_id")
    private String interactionId;

    @Column(name = "coin_name")
    private String coinName;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "quote")
    private Double quote;

    @Column(name = "gas")
    private Double gas;

    @Column(name = "date")
    private String date;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    public History() {}
    public History(int historyId, String status, String interactionId, String coinName, Double amount, Double quote, Double gas, Member member) {
        this.historyId = historyId;
        this.status = status;
        this.interactionId = interactionId;
        this.coinName = coinName;
        this.amount = amount;
        this.quote = quote;
        this.gas = gas;
        this.member = member;
    }

    public int getHistoryId() {
        return historyId;
    }
    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getInteractionId() {
        return interactionId;
    }
    public void setInteraction_id() {
        this.interactionId = interactionId;
    }
    public String getCoinName() {
        return coinName;
    }
    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public Double getQuote() {
        return quote;
    }
    public void setQuote(Double quote) {
        this.quote = quote;
    }
    public Double getGas() {
        return gas;
    }
    public void setGas(Double gas) {
        this.gas = gas;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
