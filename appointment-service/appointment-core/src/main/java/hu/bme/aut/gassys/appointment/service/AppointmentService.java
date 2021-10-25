package hu.bme.aut.gassys.appointment.service;

import hu.bme.aut.gassys.appointment.AppointmentCreationDTO;
import hu.bme.aut.gassys.appointment.AppointmentDTO;
import hu.bme.aut.gassys.appointment.data.AppointmentEntity;
import hu.bme.aut.gassys.appointment.data.AppointmentRepository;
import hu.bme.aut.gassys.appointment.exception.AppointmentException;
import hu.bme.aut.gassys.appointment.exception.AppointmentNotFoundException;
import hu.bme.aut.gassys.category.CategoryDTO;
import hu.bme.aut.gassys.category.CategoryServiceIF;
import hu.bme.aut.gassys.category.exception.CategoryException;
import hu.bme.aut.gassys.user.UserDTO;
import hu.bme.aut.gassys.user.UserServiceIF;
import hu.bme.aut.gassys.user.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final UserServiceIF userServiceClient;

    private final CategoryServiceIF categoryServiceClient;

    public AppointmentEntity create(AppointmentCreationDTO dto) {
        log.debug("Creating new Appointment {}", dto);
        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.setEventId(dto.getEventId());
        appointmentEntity.setCategoryIds(dto.getCategoryIds());
        appointmentEntity.setApplicantIds(dto.getApplicantIds());
        return appointmentRepository.save(appointmentEntity);
    }

    public Page<AppointmentEntity> findAllAppointments(Pageable pageable) {
        return appointmentRepository.findAll(pageable);
    }

    public Optional<AppointmentEntity> findOne(Integer id) {
        return appointmentRepository.findById(id);
    }

    @Transactional
    public void deleteByEventId(Integer id) {
        log.debug("Deleting appointments for event id {}", id);
        try {
            appointmentRepository.deleteAppointmentEntitiesByEventId(id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Appointment with id {} not found.", id);
            throw new AppointmentException();
        }
    }

    public void deleteOne(Integer id) {
        log.debug("Deleting appointment {}", id);
        try {
            appointmentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Appointment with id {} not found.", id);
            throw new AppointmentException();
        }
    }

    public void deleteAll() {
        log.debug("Deleting all appointments");
        appointmentRepository.deleteAll();
    }

    public boolean existsById(Integer id) {
        return appointmentRepository.existsById(id);
    }

    public AppointmentEntity modify(Integer id, AppointmentDTO appointmentDTO) {
        log.debug("Updating appointment {}", id);

        // TODO: Refactor with orElseThrow
        AppointmentEntity entity = appointmentRepository.findById(id).orElseThrow(AppointmentNotFoundException::new);
        entity.setEventId(appointmentDTO.getEventId());
        entity.setCategoryIds(appointmentDTO.getCategoryIds());
        entity.setApplicantIds(appointmentDTO.getApplicantIds());
        return appointmentRepository.save(entity);
    }

    private boolean applicantNotExists(Integer applicantId) {
        log.error("Applicant {} does not exist.", applicantId);
        ResponseEntity<UserDTO> entity = userServiceClient.findOneUser(applicantId);
        return entity.getStatusCode() != HttpStatus.OK;
    }

    public AppointmentEntity addApplicant(Integer appointmentId, Integer applicantId) {
        log.debug("Adding applicant {} to appointment {}", applicantId, appointmentId);
        AppointmentEntity appointmentEntity = appointmentRepository.findById(appointmentId)
                .orElseThrow(AppointmentNotFoundException::new);

        if (applicantNotExists(applicantId))
            throw new UserException();

        appointmentEntity.addApplicant(applicantId);

        return appointmentRepository.save(appointmentEntity);

    }

    public AppointmentEntity removeApplicant(Integer appointmentId, Integer applicantId) {
        log.debug("Removing applicant {} from appointment {}", applicantId, appointmentId);
        AppointmentEntity appointmentEntity = appointmentRepository.findById(appointmentId)
                .orElseThrow(AppointmentNotFoundException::new);

        if (applicantNotExists(applicantId))
            throw new UserException();

        appointmentEntity.removeApplicant(applicantId);

        return appointmentRepository.save(appointmentEntity);
    }

    public void removeApplicantsByApplicantId(Integer applicantId) {
        log.debug("Removing applicant {} from appointments.", applicantId);
        Set<AppointmentEntity> appointmentEntities = appointmentRepository.getAppointmentEntitiesByApplicantIdsContaining(applicantId);

        appointmentEntities.forEach(e -> e.removeApplicant(applicantId));

        appointmentRepository.saveAll(appointmentEntities);
        // TODO: Maybe return changed list for FE?
    }

    private boolean categoryNotExists(Integer categoryId) {

        ResponseEntity<CategoryDTO> entity = categoryServiceClient.findOneCategory(categoryId);
        if (entity.getStatusCode() != HttpStatus.OK){
            log.error("Category {} does not exist.", categoryId);
            return true;
        }
        return false;
    }

    public AppointmentEntity addCategory(Integer appointmentId, Integer categoryId) {
        log.debug("Adding category {} to appointment {}", categoryId, appointmentId);
        AppointmentEntity appointmentEntity = appointmentRepository.findById(appointmentId)
                .orElseThrow(AppointmentNotFoundException::new);

        if (categoryNotExists(categoryId))
            throw new CategoryException();

        appointmentEntity.addCategory(categoryId);

        return appointmentRepository.save(appointmentEntity);
    }

    public AppointmentEntity removeCategory(Integer appointmentId, Integer categoryId) {
        log.debug("Removing category {} from appointment {}", categoryId, appointmentId);
        AppointmentEntity appointmentEntity = appointmentRepository.findById(appointmentId)
                .orElseThrow(AppointmentNotFoundException::new);

        if (categoryNotExists(categoryId))
            throw new CategoryException();

        appointmentEntity.removeCategory(categoryId);

        return appointmentRepository.save(appointmentEntity);
    }

    public void removeCategoriesByCategoryId(Integer categoryId) {
        log.debug("Removing category {} from appointments.", categoryId);
        Set<AppointmentEntity> appointmentEntities = appointmentRepository.getAppointmentEntitiesByCategoryIdsContaining(categoryId);

        appointmentEntities.forEach(e -> e.removeCategory(categoryId));

        appointmentRepository.saveAll(appointmentEntities);
        // TODO: Maybe return changed list for FE?
    }

    @Transactional
    public void deleteByEventIdList(Set<Integer> eventIds) {
        for (Integer id : eventIds) {
            appointmentRepository.deleteAppointmentEntitiesByEventId(id);
        }
    }
}
