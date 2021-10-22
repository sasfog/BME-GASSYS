package hu.bme.aut.gassys.appointment.presentation;

import hu.bme.aut.gassys.appointment.AppointmentCreationDTO;
import hu.bme.aut.gassys.appointment.AppointmentDTO;
import hu.bme.aut.gassys.appointment.data.AppointmentEntity;
import hu.bme.aut.gassys.appointment.exception.AppointmentException;
import hu.bme.aut.gassys.appointment.service.AppointmentService;
import hu.bme.aut.gassys.appointment.exception.AppointmentNotFoundException;
import hu.bme.aut.gassys.category.exception.CategoryException;
import hu.bme.aut.gassys.user.exception.UserException;
import io.swagger.models.Response;
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
@RequestMapping("/api/appointment")
@Slf4j
@AllArgsConstructor
public class AppointmentController {

    private AppointmentService appointmentService;

    private AppointmentMapper appointmentMapper;

    @GetMapping
    public ResponseEntity<Page<AppointmentDTO>> getAll(Pageable pageable){
        return ResponseEntity.ok(appointmentService.findAllAppointments(pageable).map(appointmentMapper::appointmentToAppointmentDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointment(@PathVariable Integer id){
        return appointmentService.findOne(id)
                .map(appointmentEntity -> ResponseEntity.ok(appointmentMapper.appointmentToAppointmentDTO(appointmentEntity)))
                .orElseGet( () -> ResponseEntity.notFound().build());
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

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll(){
        try {
            appointmentService.deleteAll();
            return ResponseEntity.ok(HttpStatus.OK);
        }
        catch (AppointmentException e){
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Integer id){
        try {
            appointmentService.deleteOne(id);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        catch (AppointmentException e){
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> modifyAppointment(@PathVariable Integer id, @RequestBody AppointmentDTO appointmentDTO, UriComponentsBuilder builder){
        try {


            AppointmentEntity appointmentEntity;
            if (!appointmentService.existsById(id)){
                appointmentEntity = appointmentService.create(appointmentMapper.appointmentDTOToAppointmentCreationDTO(appointmentDTO));
                UriComponents uriComponents = builder.path("/api/appointment/{id}").buildAndExpand(appointmentEntity.getId());
                log.debug("Created entity : {}", appointmentEntity);
                return ResponseEntity.created(uriComponents.toUri()).body(appointmentMapper.appointmentToAppointmentDTO(appointmentEntity));
            }
            else {
                appointmentEntity = appointmentService.modify(id, appointmentDTO);
                log.debug("Updated entity: {}", appointmentEntity);
                return ResponseEntity.ok(appointmentMapper.appointmentToAppointmentDTO(appointmentEntity));
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{appointmentId}/user/{applicantId}")
    public ResponseEntity<AppointmentDTO> addApplicant(@PathVariable Integer appointmentId, @PathVariable Integer applicantId) {
        try {
            AppointmentEntity entity = appointmentService.addApplicant(appointmentId, applicantId);
            return ResponseEntity.ok(appointmentMapper.appointmentToAppointmentDTO(entity));
        }
        catch (AppointmentNotFoundException | UserException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }


    }

    @DeleteMapping("/{appointmentId}/user/{applicantId}")
    public ResponseEntity<AppointmentDTO> removeApplicant(@PathVariable Integer appointmentId, @PathVariable Integer applicantId) {
        try {
            AppointmentEntity entity = appointmentService.removeApplicant(appointmentId, applicantId);
            return ResponseEntity.ok(appointmentMapper.appointmentToAppointmentDTO(entity));
        }
        catch (AppointmentNotFoundException | UserException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{appointmentId}/category/{categoryId}")
    public ResponseEntity<AppointmentDTO> addCategory(@PathVariable Integer appointmentId, @PathVariable Integer categoryId) {
        try {
            AppointmentEntity entity = appointmentService.addCategory(appointmentId, categoryId);
            return ResponseEntity.ok(appointmentMapper.appointmentToAppointmentDTO(entity));
        }
        catch (AppointmentNotFoundException | CategoryException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{appointmentId}/category/{categoryId}")
    public ResponseEntity<AppointmentDTO> removeCategory(@PathVariable Integer appointmentId, @PathVariable Integer categoryId) {
        try {
            AppointmentEntity entity = appointmentService.removeCategory(appointmentId, categoryId);
            return ResponseEntity.ok(appointmentMapper.appointmentToAppointmentDTO(entity));
        }
        catch (AppointmentNotFoundException | CategoryException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
