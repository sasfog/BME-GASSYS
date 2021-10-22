package hu.bme.aut.gassys.category;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "category-service-v1",
        url = "$services.category-service-url:localhost:8087/category"
)
public interface CategoryServiceIF {

    @GetMapping("/{id}")
    ResponseEntity<CategoryDTO> findOneCategory(@PathVariable Integer id);
}