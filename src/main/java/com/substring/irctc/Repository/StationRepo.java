package com.substring.irctc.Repository;

import com.substring.irctc.dto.StationDto;
import com.substring.irctc.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface StationRepo extends JpaRepository<Station,Long> {
}
