package hu.bme.aut.gassys.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    @NotNull
    @NotEmpty
    private Integer id;

    @NotNull
    @NotEmpty
    private Integer organiserId;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private Instant startDateTime;

    private Integer capacity;

    private String description;

    @NotNull
    @NotEmpty
    private Integer duration;

    @NotNull
    @NotEmpty
    private String color;
}
