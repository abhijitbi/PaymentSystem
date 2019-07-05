package io.PaymentSystem.PaymentSystemService.model;


import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TokenAndKeyRepository extends MongoRepository<TokenAndKey,String>
{
    TokenAndKey getSecurekeyByToken(String token);
}
