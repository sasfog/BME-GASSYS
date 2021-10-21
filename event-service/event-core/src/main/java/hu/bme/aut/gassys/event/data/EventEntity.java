package hu.bme.aut.gassys.event.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Instant startDateTime;

    private Integer capacity;

    private String description;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private String color;
}
