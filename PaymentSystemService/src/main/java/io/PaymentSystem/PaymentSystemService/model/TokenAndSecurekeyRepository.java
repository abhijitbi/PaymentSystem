package io.PaymentSystem.PaymentSystemService.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenAndSecurekeyRepository extends MongoRepository<TokenAndSecureKey,String> {
    TokenAndSecureKey findDateastokenBySecurekey(String securekey);
}
