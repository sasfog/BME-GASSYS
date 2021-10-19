package hu.bme.aut.gassys.user.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Date birthdate;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;
}
