package io.AcquirerBank.AquirerBankService.model;


import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenAndKeyRepository extends MongoRepository<TokenAndKey,String>
{
    TokenAndKey getSecurekeyByToken(String token);
}
