package io.AcquirerBank.AquirerBankService.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenAndSecurekeyRepository extends MongoRepository<TokenAndSecureKey,String> {
    TokenAndSecureKey findSecurekeyByDateastoken(String token);
}
