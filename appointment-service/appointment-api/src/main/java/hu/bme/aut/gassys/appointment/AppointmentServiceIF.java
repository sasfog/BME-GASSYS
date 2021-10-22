package hu.bme.aut.gassys.appointment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "appointment-service-v1",
        url = "${services.appointment-service-url:localhost:8083}/api/appointment"
)
public interface AppointmentServiceIF {

    @PostMapping()
    ResponseEntity<AppointmentDTO> CreateAppointment(@RequestBody AppointmentCreationDTO dto);
}