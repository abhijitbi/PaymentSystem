package io.AcquirerBank.AquirerBankService.service;

import io.AcquirerBank.AquirerBankService.model.IINRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcquirerService
{

    @Autowired
    private IINRepository iinRepository;

    public AcquirerService() {
    }

    public void getIINNumber(int id){

        System.out.println("IIN numbers::"+iinRepository.findAll());
    }



}
