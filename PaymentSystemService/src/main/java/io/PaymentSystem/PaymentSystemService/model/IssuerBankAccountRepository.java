package io.PaymentSystem.PaymentSystemService.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IssuerBankAccountRepository extends MongoRepository<IssuerBankAccount,String> {
    IssuerBankAccount findBankByBin(String bin);
}
