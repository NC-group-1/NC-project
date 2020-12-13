package com.nc.project.dao.genericDao;

import java.io.Serializable;

public interface GenericDaoEntity<ID extends Serializable> {
    ID getId();
    void setId(ID id);
}
