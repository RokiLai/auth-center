package com.example.authservice.domain.identity.repository;

import com.example.authservice.domain.identity.model.valueobject.AuthorizationSnapshot;

import java.util.List;

public interface IdentityAuthorizationRepository {

    AuthorizationSnapshot loadSnapshotByRoleIds(List<Long> roleIds);
}
