package com.substring.irctc.Repository;

import com.substring.irctc.entity.Train;
import com.substring.irctc.entity.TrainRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainRouteRepository extends JpaRepository<TrainRoute,Long> {

    @Query("SELECT tr FROM TrainRoute tr WHERE tr.train.id = ?1 order by tr.stationOrder")
    List<TrainRoute> findByTrainId(Long trainId);
}
