package io.IssuerBankService.IssuerBankService.model;


import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenAndKeyRepository extends MongoRepository<TokenAndKey,String>
{
    TokenAndKey findSecurekeyByToken(String token);
}
