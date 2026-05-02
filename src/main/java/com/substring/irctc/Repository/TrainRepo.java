package com.substring.irctc.Repository;

import com.substring.irctc.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainRepo extends JpaRepository<Train, Long> {
}
