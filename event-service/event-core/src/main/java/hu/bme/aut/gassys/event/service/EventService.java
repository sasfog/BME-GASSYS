package hu.bme.aut.gassys.event.service;

import hu.bme.aut.gassys.event.EventCreationDTO;
import hu.bme.aut.gassys.event.EventDTO;
import hu.bme.aut.gassys.event.data.EventEntity;
import hu.bme.aut.gassys.event.data.EventRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class EventService {

    private EventRepository eventRepository;

    public EventEntity create(EventCreationDTO dto){
        log.debug("Creating new Event {}", dto);
        EventEntity eventEntity = new EventEntity();
        eventEntity.setName(dto.getName());
        eventEntity.setStartDateTime(dto.getStartDateTime());
        eventEntity.setDescription(dto.getDescription());
        eventEntity.setDuration(dto.getDuration());
        eventEntity.setCapacity(dto.getCapacity());
        eventEntity.setColor(dto.getColor());
        return eventRepository.save(eventEntity);
    }

    public Page<EventEntity> findAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    public Optional<EventEntity> findOne(Integer id) {
        return eventRepository.findById(id);
    }

    public void deleteOne(Integer id){
        log.debug("Creating event {}", id);
        try {
            eventRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            log.warn("Event with id {} not found.", id);
            throw new EventException();
        }
    }

    public void deleteAll() {
        log.debug("Creating all events");
        eventRepository.deleteAll();
    }

    public boolean existsById(Integer id) {
        return eventRepository.existsById(id);
    }

    public EventEntity modify(Integer id, EventDTO eventDTO) {
        log.debug("Updating event {} to {}", id, eventDTO.getName());

        EventEntity entity = eventRepository.findById(id).get();
        entity.setName(eventDTO.getName());
        entity.setStartDateTime(eventDTO.getStartDateTime());
        entity.setDescription(eventDTO.getDescription());
        entity.setDuration(eventDTO.getDuration());
        entity.setCapacity(eventDTO.getCapacity());
        entity.setColor(eventDTO.getColor());
        return eventRepository.save(entity);
    }
}
