package com.substring.irctc.entity;

import com.substring.irctc.annotations.ValidCoach;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Entity
@Table(name = "trains")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Train {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
    private String name;
    private String number;

//    private String routeName;

    private Integer totalDistance;

    @ManyToOne
    @JoinColumn(name = "source_station_id")
    private Station sourceStation ;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Station destinationStation;

    @OneToMany(mappedBy = "train")
    private List<TrainRoute> routes;

    @OneToMany(mappedBy = "train")
    private List<TrainSchedule> schedules;

    @OneToOne(cascade = CascadeType.ALL)
    private TrainImage trainImage;


}
