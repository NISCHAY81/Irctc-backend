package com.substring.irctc.Repository;

import com.substring.irctc.entity.Train;
import com.substring.irctc.entity.TrainRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrainRepo extends JpaRepository<Train, Long> {

    @Query("SELECT tr.train FROM TrainRoute tr WHERE tr.station.id = :sourceStationId OR tr.station.id = :destinationStationId")
    List<Train> findTrainBySourceAndDestinationInOrder(
            @Param("sourceStationId") Long sourceStationId,
            @Param("destinationStationId") Long destinationStationId
    );
}