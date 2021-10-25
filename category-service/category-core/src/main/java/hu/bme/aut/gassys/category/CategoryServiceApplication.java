package hu.bme.aut.gassys.category;

import hu.bme.aut.gassys.common.configuration.ApiDocsConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        ApiDocsConfiguration.class
})
@Slf4j
@EnableFeignClients(basePackages = "hu.bme.aut.gassys")
public class CategoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CategoryServiceApplication.class, args);
        log.info("CategoryService has successfully started!");
    }
}