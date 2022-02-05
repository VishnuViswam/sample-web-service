package com.sample.webservice.serviceimpl.v1;

import com.sample.webservice.entity.AppConfigSettings;
import com.sample.webservice.repositories.AppConfigSettingsRepository;
import com.sample.webservice.service.v1.AppConfigSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class to implement the config settings operations.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@Service("AppConfigSettingsService")
@Scope("prototype")
public class AppConfigSettingsServiceImpl implements AppConfigSettingsService {

    @Autowired
    private AppConfigSettingsRepository appConfigSettingsRepository;

    @Override
    public AppConfigSettings findByConfigKeyAndStatus(String jwtSecretKey, short status) {
        return appConfigSettingsRepository.findByConfigKeyAndStatus(jwtSecretKey, status);
    }

    @Override
    public List<AppConfigSettings> findByConfigKeyInAndStatus(List<String> keyList, short status) {
        return appConfigSettingsRepository.findByConfigKeyInAndStatus(keyList,status);
    }
}
