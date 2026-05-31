package com.substring.irctc.Repository;

import com.substring.irctc.entity.TrainSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainScheduleRepo extends JpaRepository<TrainSchedule, Long> {
    @Query("SELECT ts FROM TrainSchedule ts WHERE ts.train.id = ?1")
    List<TrainSchedule> findByTrainId(Long trainId);

    @Query("SELECT ts FROM TrainSchedule ts WHERE ts.train.id = ?1 AND ts.runDate = ?2")
    Optional<TrainSchedule> findByTrainAndRunDate(Long trainId, LocalDate runDate);
}
