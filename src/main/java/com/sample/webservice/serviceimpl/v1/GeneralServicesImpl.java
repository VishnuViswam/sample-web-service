package com.sample.webservice.serviceimpl.v1;

import com.sample.webservice.service.v1.GeneralServices;
import com.sample.webservice.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Service class to implement the common operations.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@Service("GeneralServices")
@Scope("prototype")
public class GeneralServicesImpl implements GeneralServices {

    private static final Logger logger = LoggerFactory.getLogger(GeneralServicesImpl.class);

    @Override
    public String buildJsonData(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String data = null;
        if (object != null) {
            try {
                data = mapper.writeValueAsString(object);
            } catch (IOException e) {
                logger.error("Object conversion to json String : exception : ", e);
                e.printStackTrace();
            }
        }
        return data;
    }

    @Override
    public long getApiRequestedUserId(HttpServletRequest httpServletRequest) {
        return (Long) httpServletRequest.getAttribute(Constants.API_REQUESTED_USER_ACCOUNT_ID_KEY);
    }

}
