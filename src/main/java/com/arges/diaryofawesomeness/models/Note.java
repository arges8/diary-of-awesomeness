package com.arges.diaryofawesomeness.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;


@Data
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Day has to be set")
    private Date day;

    @ElementCollection
    private List<String> positiveEvents;

}
