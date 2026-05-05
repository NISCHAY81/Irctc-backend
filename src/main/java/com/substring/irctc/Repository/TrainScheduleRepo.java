package com.substring.irctc.Repository;

import com.substring.irctc.entity.TrainSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainScheduleRepo extends JpaRepository<TrainSchedule, Long> {
    List<TrainSchedule> findByTrainId(Long trainId);
}
