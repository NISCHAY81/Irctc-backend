package com.substring.irctc.dto;

import com.substring.irctc.entity.Station;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainDTO {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private  Long id;
    @NotEmpty(message = "train number is required !!")
    @Size(min = 3, max = 10, message = "Invalid length of train no.")
    @Pattern(regexp="\\d+$", message = "Invalid no train no contains only numbers.")
    private String number;

    private String name;

//    private String routeName;

    private Integer totalDistance;

    private StationDto sourceStation;


    private StationDto destinationStation;


}
