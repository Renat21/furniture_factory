package com.example.factory.repository;

import com.example.factory.entity.Providers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvidersRepository extends JpaRepository<Providers, Long> {

    Providers findProvidersById(Long id);

    void deleteProvidersById(Long id);

    Providers findProvidersByName(String name);
}