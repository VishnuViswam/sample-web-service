package com.sample.webservice.service;

import com.sample.webservice.entity.AppConfigSettings;

import java.util.List;

/**
 * Interface to declare the config settings services .
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
public interface AppConfigSettingsService {

    /**
     * To find the config settings using key and status
     *
     * @param jwtSecretKey
     * @param status
     * @return
     */
    AppConfigSettings findByConfigKeyAndStatus(String jwtSecretKey, short status);

    /**
     * To find the config settings list using key and status
     *
     * @param keyList
     * @param status
     * @return
     */
    List<AppConfigSettings> findByConfigKeyInAndStatus(List<String> keyList, short status);
}
