package com.timtrix.entities;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Organisation {

    private String orgId;
    @NotEmpty(message = "Name is required")
    private String name;
    private String description;

}
