package com.example.authservice.domain.repo;

import java.util.List;

public interface PermissionRepo {

    List<String>  selectCodeByIds(List<Long> permissionIds);
}
