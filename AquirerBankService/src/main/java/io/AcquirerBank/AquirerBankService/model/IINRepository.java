package io.AcquirerBank.AquirerBankService.model;

import io.AcquirerBank.AquirerBankService.model.IINumber;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IINRepository extends MongoRepository<IINumber,String>
{

    IINumber getNetworkByIin(int i);

}
