package com.mapper;

import com.entity.UserRole;

public interface UserRoleMapper {

    Integer InsertUserRole(UserRole userRole);

    Integer LookUserRoleId(String userid);

    void UpdateUserRole(UserRole userRole);
}
