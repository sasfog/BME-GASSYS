package hu.bme.aut.gassys.event.presentation;



import feign.FeignException;
import hu.bme.aut.gassys.event.EventCreationDTO;
import hu.bme.aut.gassys.event.EventDTO;
import hu.bme.aut.gassys.event.data.EventEntity;
import hu.bme.aut.gassys.event.service.EventException;
import hu.bme.aut.gassys.event.service.EventService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/event")
@Slf4j
@AllArgsConstructor
public class EventController {
    
    private final EventService eventService;
    
    private final EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<Page<EventDTO>> getAll(Pageable pageable){
            return ResponseEntity.ok(eventService.findAllEvents(pageable).map(eventMapper::eventToEventDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEvent(@PathVariable Integer id){
        return eventService.findOne(id)
                .map(eventEntity -> ResponseEntity.ok(eventMapper.eventToEventDTO(eventEntity)))
                .orElseGet( () -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventCreationDTO eventCreationDTO, UriComponentsBuilder builder){
        try{
            EventEntity eventEntity = eventService.create(eventCreationDTO);

            UriComponents uriComponents = builder.path("/api/event/{id}").buildAndExpand(eventEntity.getId());
            return ResponseEntity.created(uriComponents.toUri()).body(eventMapper.eventToEventDTO(eventEntity));
        }
        catch (FeignException e){
            return ResponseEntity.internalServerError().build();
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll(){
        try {
            eventService.deleteAll();
            return ResponseEntity.ok(HttpStatus.OK);
        }
        catch (EventException e){
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Integer id){
        try {
            eventService.deleteOne(id);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        catch (EventException e){
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> modifyEvent(@PathVariable Integer id, @RequestBody EventDTO eventDTO, UriComponentsBuilder builder){
        try {


            EventEntity eventEntity;
            if (!eventService.existsById(id)){
                eventEntity = eventService.create(eventMapper.eventDTOToEventCreationDTO(eventDTO));
                UriComponents uriComponents = builder.path("/api/event/{id}").buildAndExpand(eventEntity.getId());
                log.debug("Creationd entity : {}", eventEntity);
                return ResponseEntity.created(uriComponents.toUri()).body(eventMapper.eventToEventDTO(eventEntity));
            }
            else {
                eventEntity = eventService.modify(id, eventDTO);
                log.debug("Updated entity: {}", eventEntity);
                return ResponseEntity.ok(eventMapper.eventToEventDTO(eventEntity));
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
