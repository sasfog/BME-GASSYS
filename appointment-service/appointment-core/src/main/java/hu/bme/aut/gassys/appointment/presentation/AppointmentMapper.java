package hu.bme.aut.gassys.appointment.presentation;

import hu.bme.aut.gassys.appointment.AppointmentCreationDTO;
import hu.bme.aut.gassys.appointment.AppointmentDTO;
import hu.bme.aut.gassys.appointment.data.AppointmentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    AppointmentDTO appointmentToAppointmentDTO(AppointmentEntity appointmentEntity);
    AppointmentCreationDTO appointmentToAppointmentCreationDTO(AppointmentEntity appointmentEntity);
    AppointmentCreationDTO appointmentDTOToAppointmentCreationDTO(AppointmentDTO appointmentDTO);
}