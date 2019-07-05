package io.IssuerBankService.IssuerBankService.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("issuerbankaccounts")
public class IssuerBankAccount
{
    private String bin;
    private String bank;

    public IssuerBankAccount() {
    }

    public IssuerBankAccount(String bin, String bank) {
        this.bin = bin;
        this.bank = bank;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
