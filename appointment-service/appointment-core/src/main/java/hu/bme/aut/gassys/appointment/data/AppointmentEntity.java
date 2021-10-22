package hu.bme.aut.gassys.appointment.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Integer organiserId;

    @Column(nullable = false)
    private Integer eventId;

    @ElementCollection
    private Set<Integer> applicantIds;

    @ElementCollection
    private Set<Integer> categoryIds;

    public boolean addApplicant(Integer applicantId) {
        return applicantIds.add(applicantId);
    }

    public boolean removeApplicant(Integer applicantId) {
        return applicantIds.remove(applicantId);
    }

    public boolean addCategory(Integer categoryId) {
        return categoryIds.add(categoryId);
    }

    public boolean removeCategory(Integer categoryId) {
        return categoryIds.remove(categoryId);
    }


}
