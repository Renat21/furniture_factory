package com.example.factory.repository;

import com.example.factory.entity.Raws;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawsRepository extends JpaRepository<Raws, Long> {

    Raws findRawsById(Long id);

    Raws findRawsByName(String name);
}