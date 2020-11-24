package com.sample.webservice.repositories;

import com.sample.webservice.entity.AppConfigSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface used to handle application configuration details from DB.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@Repository("AppConfigSettingsRepository")
public interface AppConfigSettingsRepository extends JpaRepository<AppConfigSettings,Long> {

    /**
     * To find Config object by config key
     *
     * @param key
     * @param active
     * @return
     */
    AppConfigSettings findByConfigKeyAndStatus(String key, short active);


    /**
     * To load Config data by passing key list
     *
     * @param keyList
     * @param active
     * @return
     */
    List<AppConfigSettings> findByConfigKeyInAndStatus(List<String> keyList, short active);

}
