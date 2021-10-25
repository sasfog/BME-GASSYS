package hu.bme.aut.gassys.event;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "event-service-v1",
        url = "${services.event-service-url:localhost:8082}/api/internal/event"
)
public interface EventServiceIF {

    @DeleteMapping
    ResponseEntity<HttpStatus> deleteEventByOrganiserId(@RequestParam Integer id);
}