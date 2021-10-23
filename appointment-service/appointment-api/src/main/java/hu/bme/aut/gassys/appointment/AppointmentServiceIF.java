package hu.bme.aut.gassys.appointment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "appointment-service-v1",
        url = "${services.appointment-service-url:localhost:8083}/api/appointment"
)
public interface AppointmentServiceIF {

    @PostMapping()
    ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentCreationDTO dto);

    @DeleteMapping()
    ResponseEntity<HttpStatus> deleteAppointmentByEventId(@RequestParam Integer id);

    @DeleteMapping("/event")
    ResponseEntity<HttpStatus> deleteAppointmentByEventIds(@RequestBody EventIdListDTO dto);
}