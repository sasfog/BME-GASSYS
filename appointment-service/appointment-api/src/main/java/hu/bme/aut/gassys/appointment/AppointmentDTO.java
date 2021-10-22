package hu.bme.aut.gassys.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    @NotNull
    @NotEmpty
    private Integer id;

    @NotNull
    @NotEmpty
    private Integer organiserId;

    @NotNull
    @NotEmpty
    private Integer eventId;

    private Set<Integer> applicantIds;

    private Set<Integer> categoryIds;
}
