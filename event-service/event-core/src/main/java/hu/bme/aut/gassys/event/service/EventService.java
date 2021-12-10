package hu.bme.aut.gassys.event.service;

import feign.FeignException;
import hu.bme.aut.gassys.appointment.AppointmentCreationDTO;
import hu.bme.aut.gassys.appointment.AppointmentServiceIF;
import hu.bme.aut.gassys.appointment.EventIdListDTO;
import hu.bme.aut.gassys.event.EventCreationDTO;
import hu.bme.aut.gassys.event.EventDTO;
import hu.bme.aut.gassys.event.data.EventEntity;
import hu.bme.aut.gassys.event.data.EventRepository;
import hu.bme.aut.gassys.user.UserServiceIF;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    private final AppointmentServiceIF appointmentServiceClient;

    private final UserServiceIF userServiceClient;

    public EventEntity create(EventCreationDTO dto) {
        log.debug("Creating new Event {}", dto);

        try {
            userServiceClient.findOneUser(dto.getOrganiserId());
        } catch (FeignException e) {
            e.printStackTrace();
            log.error("Error during event creation.");
            log.error("{}", e.getMessage());
            throw e;
        }

        EventEntity eventEntity = new EventEntity();
        eventEntity.setName(dto.getName());
        eventEntity.setStartDateTime(dto.getStartDateTime());
        eventEntity.setDescription(dto.getDescription());
        eventEntity.setDuration(dto.getDuration());
        eventEntity.setCapacity(dto.getCapacity());
        eventEntity.setColor(dto.getColor());
        eventEntity.setOrganiserId(dto.getOrganiserId());


        eventEntity = eventRepository.save(eventEntity);
        AppointmentCreationDTO appointmentCreationDTO = new AppointmentCreationDTO();
        appointmentCreationDTO.setEventId(eventEntity.getId());

        // TODO: Can this be handled better?
        try {
            appointmentServiceClient.createAppointment(appointmentCreationDTO);
        } catch (FeignException e) {
            e.printStackTrace();
            log.error("Error during event creation.");
            log.error("{}", e.getMessage());
            throw e;
        }
        return eventEntity;
    }

    public Page<EventEntity> findAllEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    public Optional<EventEntity> findOne(Integer id) {
        return eventRepository.findById(id);
    }

    public void deleteOne(Integer id) {
        log.debug("Deleting event {}", id);

        if (!eventRepository.existsById(id)) {
            log.warn("Error by id {} not found", id);
            throw new EventException();
        }

        try {
            appointmentServiceClient.deleteAppointmentByEventId(id);
            eventRepository.deleteById(id);
        } catch (FeignException e) {
            e.printStackTrace();
            log.error("Error during event deletion. Was unable to delete appointments");
            log.error("{}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void deleteEventsByOrganiserId(Integer id) {
        log.debug("Deleting events for organiser {}", id);
        List<EventEntity> entities = eventRepository.getEventEntitiesByOrganiserId(id);
        Set<Integer> eventIds = new HashSet<>();
        EventIdListDTO eventIdListDTO = new EventIdListDTO();
        for (EventEntity entity : entities) {
            eventIds.add(entity.getId());
        }
        eventIdListDTO.setEventId(eventIds);

        try {
            appointmentServiceClient.deleteAppointmentByEventIds(eventIdListDTO);
            eventRepository.deleteEventEntitiesByOrganiserId(id);
        }
        catch (FeignException e) {
            e.printStackTrace();
            log.error("Error during deletion of events by organiser {}", id);
            log.error("{}",e.getMessage());
            throw e;
        }

    }

    public void deleteAll() {
        log.debug("Deleting all events");
        eventRepository.deleteAll();
    }

    public boolean existsById(Integer id) {
        return eventRepository.existsById(id);
    }

    public EventEntity modify(Integer id, EventDTO eventDTO) {
        log.debug("Updating event {} to {}", id, eventDTO.getName());

        // TODO: Refactor with orElseThrow
        EventEntity entity = eventRepository.findById(id).orElseThrow(EventException::new);
        entity.setName(eventDTO.getName());
        entity.setStartDateTime(eventDTO.getStartDateTime());
        entity.setDescription(eventDTO.getDescription());
        entity.setDuration(eventDTO.getDuration());
        entity.setCapacity(eventDTO.getCapacity());
        entity.setColor(eventDTO.getColor());
        entity.setOrganiserId(eventDTO.getOrganiserId());
        return eventRepository.save(entity);
    }

    public List<EventEntity> findEventsByWeek(Instant startDate, Instant endDate) {
        return eventRepository.getEventEntitiesByStartDateTimeBetween(startDate, endDate);
    }

}
