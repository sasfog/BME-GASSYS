package hu.bme.aut.gassys.event.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Integer>{

    void deleteEventEntitiesByOrganiserId(Integer organiserId);

    List<EventEntity> getEventEntitiesByOrganiserId(Integer organiserId);

    List<EventEntity> getEventEntitiesByStartDateTimeBetween(Instant startDate, Instant EndDate);
}
