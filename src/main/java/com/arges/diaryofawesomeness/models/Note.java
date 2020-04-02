package com.arges.diaryofawesomeness.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Data
@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date day;

    @ElementCollection
    private List<String> positiveEvents;
}
