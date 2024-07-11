package com.timtrix.repositories;

import com.timtrix.entities.Organisation;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OrganisationRepository {
    private Map<String, Organisation> organisations = new ConcurrentHashMap<>();

    public Organisation save(Organisation organisation) {
        organisations.put(organisation.getOrgId(), organisation);
        return organisation;
    }

    public Optional<Organisation> findById(String orgId) {
        return Optional.ofNullable(organisations.get(orgId));
    }

    public void deleteById(String orgId) {
        organisations.remove(orgId);
    }

    // Other CRUD methods as needed
}
