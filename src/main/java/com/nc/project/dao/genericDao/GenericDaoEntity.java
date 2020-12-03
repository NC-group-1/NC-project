package com.nc.project.dao.genericDao;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;

public interface GenericDaoEntity<T, ID extends Serializable> {
    RowMapper<T> returnRowMapper();
    ID getId();
    void setId(ID id);
    String returnTableName();
    Object[] returnQueryArgs();
}
