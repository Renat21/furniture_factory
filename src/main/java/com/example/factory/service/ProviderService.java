package com.example.factory.service;


import com.example.factory.entity.Providers;
import com.example.factory.repository.ProvidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProviderService {
    @Autowired
    ProvidersRepository providersRepository;

    public Providers findProviderById(Long id){
        return providersRepository.findProvidersById(id);
    }

    @Transactional
    public void deleteProviderById(Long id){
        providersRepository.deleteProvidersById(id);
    }

    public Providers save(Providers providers){
        return providersRepository.save(providers);
    }
}
