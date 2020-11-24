package com.sample.webservice;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Main class where spring boot application starts.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@SpringBootApplication
@EnableEncryptableProperties
public class SampleWebservice extends SpringBootServletInitializer {
	private static final Logger logger = LogManager.getLogger(SampleWebservice.class);

	{
		logger.info("<<<----- SampleWebservice application is starting ----->>>");

	}

	public static void main(String[] args) {
		SpringApplication.run(SampleWebservice.class, args);
		logger.info("<<<----- SampleWebservice application is started ----->>>");
	}

}
