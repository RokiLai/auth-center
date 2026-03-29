package com.example.authservice.domain.identity.service;

import com.example.authservice.domain.identity.model.valueobject.AuthorizationSnapshot;

import java.util.List;

public interface AuthorizationSnapshotProvider {

    AuthorizationSnapshot loadByRoleIds(List<Long> roleIds);
}
