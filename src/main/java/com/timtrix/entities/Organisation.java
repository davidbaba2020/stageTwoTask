package com.timtrix.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID orgId;
    @NotEmpty(message = "Name is required")
    private String name;
    private String description;
    @ManyToMany
    private Set<User> users = new HashSet<>();

    public Organisation(String orgName, String defaultDescription) {
        this.name=orgName;
        this.description=defaultDescription;
        this.orgId = UUID.randomUUID();
    }

}
