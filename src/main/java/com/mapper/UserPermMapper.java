package com.mapper;

import java.util.List;

public interface UserPermMapper {

    List<String> LookPermsByUserid(Integer roleid);
}
