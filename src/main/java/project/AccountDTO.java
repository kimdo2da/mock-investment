	package project;

public class AccountDTO {

    private int accountId;
    private String userId;
    private String accountName;     //추가됨: 계좌 별명
    private String accountNumber;
    private long balance;
    private long available;
    private long investing;
    private long profit;
    private String password;

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }


    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountName() {     // 추가됨
        return accountName;
    }

    public void setAccountName(String accountName) {    //추가됨
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getAvailable() {
        return available;
    }

    public void setAvailable(long available) {
        this.available = available;
    }

    public long getInvesting() {
        return investing;
    }

    public void setInvesting(long investing) {
        this.investing = investing;
    }

    public long getProfit() {
        return profit;
    }

    public void setProfit(long profit) {
        this.profit = profit;
    }
}
