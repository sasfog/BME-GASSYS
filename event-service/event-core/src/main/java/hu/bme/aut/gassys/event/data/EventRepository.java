package hu.bme.aut.gassys.event.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Integer>{
}
