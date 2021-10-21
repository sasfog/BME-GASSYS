package hu.bme.aut.gassys.event.presentation;

import hu.bme.aut.gassys.event.EventCreationDTO;
import hu.bme.aut.gassys.event.EventDTO;
import hu.bme.aut.gassys.event.data.EventEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDTO eventToEventDTO(EventEntity eventEntity);
    EventCreationDTO eventToEventCreationDTO(EventEntity eventEntity);
    EventCreationDTO eventDTOToEventCreationDTO(EventDTO eventDTO);
}