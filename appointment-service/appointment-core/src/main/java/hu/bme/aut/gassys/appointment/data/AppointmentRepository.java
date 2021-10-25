package hu.bme.aut.gassys.appointment.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;


public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Integer> {

    void deleteAppointmentEntitiesByEventId(Integer eventId);

    Set<AppointmentEntity> getAppointmentEntitiesByCategoryIdsContaining(Integer categoryId);

    Set<AppointmentEntity> getAppointmentEntitiesByApplicantIdsContaining(Integer eventId);
}
