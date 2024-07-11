package com.timtrix.repositories;

import com.timtrix.entities.Organisation;
import com.timtrix.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrganisationRepository extends JpaRepository<Organisation, UUID> {
}