package com.nc.project.dto;

import com.nc.project.dao.genericDao.GenericDaoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSetGeneralInfoDto implements GenericDaoEntity<DataSetGeneralInfoDto, Integer> {
    private Integer id;
    private String name;
    private String description;
    private Integer createdById;
    private String createdByRole;
    private String createdByName;
    private String createdBySurname;

    @Override
    public RowMapper<DataSetGeneralInfoDto> returnRowMapper() {
        return (resultSet, i) -> new DataSetGeneralInfoDto(
                resultSet.getObject("data_set_id", Integer.class),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getObject("user_id", Integer.class),
                resultSet.getString("role"),
                resultSet.getString("username"),
                resultSet.getString("surname")
        );
    }

    @Override
    public String returnTableName() {
        return "data_set";
    }

    @Override
    public Object[] returnQueryArgs() {
        return new Object[]{name, description, createdById, id};
    }
}
