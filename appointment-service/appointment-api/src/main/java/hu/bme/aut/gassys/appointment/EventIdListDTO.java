package hu.bme.aut.gassys.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventIdListDTO {

    private Set<Integer> eventId;
}
