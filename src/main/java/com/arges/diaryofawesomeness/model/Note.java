package com.arges.diaryofawesomeness.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Set;


@Data
@Entity
public class Note {

    @ManyToOne
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Day has to be set")
    private Date day;

    @ElementCollection
    private Set<String> positiveEvents;

}
