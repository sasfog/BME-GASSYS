package hu.bme.aut.gassys.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user-service-v1",
        url = "$services.user-service-url:localhost:8081/user"
)
public interface UserServiceIF {

    @GetMapping("/{id}")
    ResponseEntity<UserDTO> findOneUser(@PathVariable Integer id);
}
