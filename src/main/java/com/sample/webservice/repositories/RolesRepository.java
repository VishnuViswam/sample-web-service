package com.sample.webservice.repositories;

import com.sample.webservice.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface used to handle User Role details from DB.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@Repository("RolesRepository")
public interface RolesRepository extends JpaRepository<Roles, Integer> {

    /**
     * To find role using id and status
     *
     * @param roleId
     * @param activeStatus
     * @return
     */
    Roles findByIdAndStatus(int roleId, short activeStatus);
}
