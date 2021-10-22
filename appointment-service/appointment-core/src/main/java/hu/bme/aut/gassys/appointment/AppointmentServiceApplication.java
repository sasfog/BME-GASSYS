package hu.bme.aut.gassys.appointment;

import hu.bme.aut.gassys.common.configuration.ApiDocsConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        ApiDocsConfiguration.class
})
@Slf4j
public class AppointmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppointmentServiceApplication.class, args);
        log.info("AppointmentService has successfully started!");
    }
}
