package hu.bme.aut.gassys.appointment.presentation;

import hu.bme.aut.gassys.appointment.AppointmentCreationDTO;
import hu.bme.aut.gassys.appointment.AppointmentDTO;
import hu.bme.aut.gassys.appointment.EventIdListDTO;
import hu.bme.aut.gassys.appointment.data.AppointmentEntity;
import hu.bme.aut.gassys.appointment.exception.AppointmentException;
import hu.bme.aut.gassys.appointment.service.AppointmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/internal/appointment")
@Slf4j
@AllArgsConstructor
public class InternalAppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;

    @DeleteMapping()
    public ResponseEntity<HttpStatus> deleteAppointmentByEventId(@RequestParam Integer eventId){
        try {
            appointmentService.deleteByEventId(eventId);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        catch (AppointmentException e){
            return ResponseEntity.noContent().build();
        }
    }



    @DeleteMapping("/event")
    ResponseEntity<HttpStatus> deleteAppointmentByEventIds(@RequestBody EventIdListDTO dto){
        try {
            appointmentService.deleteByEventIdList(dto.getEventId());
            return ResponseEntity.noContent().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentCreationDTO appointmentCreationDTO, UriComponentsBuilder builder){
        try{
            AppointmentEntity appointmentEntity = appointmentService.create(appointmentCreationDTO);

            UriComponents uriComponents = builder.path("/api/appointment/{id}").buildAndExpand(appointmentEntity.getId());
            return ResponseEntity.created(uriComponents.toUri()).body(appointmentMapper.appointmentToAppointmentDTO(appointmentEntity));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<HttpStatus> removeApplicantAndCategoryFromAllAppointment(@RequestParam(required = false) Integer applicantId, @RequestParam(required = false) Integer categoryId) {
        if (applicantId != null)
            appointmentService.removeApplicantsByApplicantId(applicantId);
        if (categoryId != null)
            appointmentService.removeCategoriesByCategoryId(categoryId);
        return ResponseEntity.ok().build();
    }
}
