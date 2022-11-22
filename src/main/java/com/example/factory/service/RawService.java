package com.example.factory.service;


import com.example.factory.entity.Employee;
import com.example.factory.entity.Providers;
import com.example.factory.entity.Raws;
import com.example.factory.repository.ProvidersRepository;
import com.example.factory.repository.RawsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class RawService {
    @Autowired
    RawsRepository rawsRepository;

    @Autowired
    ProvidersRepository providersRepository;

    public Raws findRawById(Long id){
        return rawsRepository.findRawsById(id);
    }


    public Raws save(Raws raws, Providers providers){
        raws.setProviders(providers);
        Raws newRaw = rawsRepository.save(raws);
        providers.getRaws().add(newRaw);
        providersRepository.save(providers);
        return newRaw;
    }

    public Raws updateRaw(Raws newRaw, Long rawId, String providerId){
        Raws raw = findRawById(rawId);

        Providers oldProvider = raw.getProviders();
        oldProvider.getRaws().remove(raw);
        providersRepository.save(oldProvider);


        Providers newProvider = providersRepository.findProvidersByName(providerId);

        raw.setProviders(newProvider);
        raw.setName(newRaw.getName());
        raw.setPrice(newRaw.getPrice());
        raw.setQuantity(newRaw.getQuantity());
        Raws dbRaw = rawsRepository.save(raw);

        newProvider.getRaws().add(dbRaw);
        providersRepository.save(newProvider);

        return dbRaw;
    }

    @Transactional
    public void deleteRawById(Long id){
        Raws raw = findRawById(id);
        Providers providers = raw.getProviders();
        providers.getRaws().remove(raw);
        providersRepository.save(providers);
        rawsRepository.deleteById(id);
    }
}
