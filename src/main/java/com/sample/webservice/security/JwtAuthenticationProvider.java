package com.sample.webservice.security;

import com.sample.webservice.entity.AppConfigSettings;
import com.sample.webservice.entity.UserAccounts;
import com.sample.webservice.models.JwtAuthenticationToken;
import com.sample.webservice.models.JwtUserDetails;
import com.sample.webservice.repositories.AppConfigSettingsRepository;
import com.sample.webservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to handle JWT configuration.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private JwtTokenHandler jwtTokenHandler;

	@Autowired
	private AppConfigSettingsRepository appConfigSettingsRepository;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

	}

	@Override
	protected UserDetails retrieveUser(String username,
                                       UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

		JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) usernamePasswordAuthenticationToken;
		String accessToken = jwtAuthenticationToken.getToken();
		List<AppConfigSettings> configList = new ArrayList<AppConfigSettings>();
		List<String> keyList = new ArrayList<String>();
		keyList.add(Constants.JWT_ACCESS_TOKEN_EXPIRATION_TIME);
		keyList.add(Constants.JWT_SECRET_KEY);
		keyList.add(Constants.JWT_REFRESH_TOKEN_EXPIRATION_TIME);
		
		configList = appConfigSettingsRepository.findByConfigKeyInAndStatus(keyList, Constants.ACTIVE_STATUS);
		String JWT_ACCESS_TOKEN_EXPIRATION_TIME = null;
		String JWT_SECRET_KEY = null;
		String JWT_REFRESH_TOKEN_EXPIRATION_TIME = null;
		for (AppConfigSettings configSettingObject : configList) {
			if (Constants.JWT_ACCESS_TOKEN_EXPIRATION_TIME.equals(configSettingObject.getConfigKey())) {
				JWT_ACCESS_TOKEN_EXPIRATION_TIME = configSettingObject.getConfigValue();
			} else if (Constants.JWT_SECRET_KEY.equals(configSettingObject.getConfigKey())) {
				JWT_SECRET_KEY = configSettingObject.getConfigValue();
			}else if (Constants.JWT_REFRESH_TOKEN_EXPIRATION_TIME.equals(configSettingObject.getConfigKey())) {
				JWT_REFRESH_TOKEN_EXPIRATION_TIME = configSettingObject.getConfigValue();
			}

		}
		UserAccounts jwtUser = jwtTokenHandler.validate(JWT_SECRET_KEY, accessToken);
		String tokens = null;
		if (jwtUser == null) {
			// access token is expired
			logger.error("Authentication : Error : access token is expired");
			// return new JwtUserDetails(null, null, null, null);
			throw new RuntimeException(Constants.TOKEN_IS_EXPIRED);

		}

		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(jwtUser.getRole());
		return new JwtUserDetails(jwtUser.getId(), jwtUser.getRole(), tokens, grantedAuthorities);
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return (JwtAuthenticationToken.class.isAssignableFrom(aClass));
	}
}
