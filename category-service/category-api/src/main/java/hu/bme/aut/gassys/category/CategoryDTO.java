package hu.bme.aut.gassys.category;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
public class CategoryDTO {

    @NotNull
    @NotEmpty
    private Integer id;

    @NotEmpty
    @NotNull
    private String name;
}
