package hu.bme.aut.gassys.appointment.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Integer eventId;

    @ElementCollection
    private Set<Integer> applicantIds;

    @ElementCollection
    private Set<Integer> categoryIds;

    public void addApplicant(Integer applicantId) {
        applicantIds.add(applicantId);
    }

    public void removeApplicant(Integer applicantId) {
        applicantIds.remove(applicantId);
    }

    public void addCategory(Integer categoryId) {
        categoryIds.add(categoryId);
    }

    public void removeCategory(Integer categoryId) {
        categoryIds.remove(categoryId);
    }


}
