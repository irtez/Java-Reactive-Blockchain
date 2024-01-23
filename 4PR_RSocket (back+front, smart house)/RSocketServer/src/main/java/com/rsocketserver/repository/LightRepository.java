package com.rsocketserver.repository;

import com.rsocketserver.model.Light;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface LightRepository extends JpaRepository<Light, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Light e SET e.isOn = :newIsOn WHERE e.id = :id")
    void updateLight(@Param("id") Long id_light, @Param("newIsOn") Boolean newIsOn);
}
