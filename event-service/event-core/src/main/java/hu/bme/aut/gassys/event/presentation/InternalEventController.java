package hu.bme.aut.gassys.event.presentation;

import hu.bme.aut.gassys.event.service.EventException;
import hu.bme.aut.gassys.event.service.EventService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/internal/event")
@Slf4j
@AllArgsConstructor
public class InternalEventController {

    private final EventService eventService;

    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteEventByOrganiserId(@RequestParam Integer id) {
        try {
            eventService.deleteEventsByOrganiserId(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (EventException e) {
            return ResponseEntity.noContent().build();
        }
    }
}
