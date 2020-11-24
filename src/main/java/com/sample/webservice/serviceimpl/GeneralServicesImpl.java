package com.sample.webservice.serviceimpl;

import com.sample.webservice.service.GeneralServices;
import com.sample.webservice.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    private static final Logger logger = LogManager.getLogger(GeneralServicesImpl.class);

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
