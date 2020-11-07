package com.mobility.repository;

import com.mobility.model.entity.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StationRepository extends MongoRepository<Station, String> {

    Optional<Station> findById(Integer id);

    Optional<Station> findByNameIgnoreCase(String name);

    Page<Station> findAllByIpIdNotNull(Pageable pageable);

    Page<Station> findAllByMetroIdNotNull(Pageable pageable);

}
