package io.mosip.pms.partner.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import io.mosip.pms.common.helper.FilterHelper;
import io.mosip.pms.common.helper.SearchHelper;
import io.mosip.pms.common.helper.WebSubPublisher;
import io.mosip.pms.common.util.PageUtils;
import io.mosip.pms.common.util.RestUtil;
import io.mosip.pms.common.validator.FilterColumnValidator;


@Import(value = {WebSubPublisher.class,RestUtil.class,FilterColumnValidator.class,FilterHelper.class,SearchHelper.class,PageUtils.class
		})
@SpringBootApplication(scanBasePackages = {"io.mosip.pms.*"})
public class PartnerServiceApplicationTest {
	
	/**
	 * Function to run the Master-Data-Service application
	 * 
	 * @param args The arguments to pass will executing the main function
	 */
	public static void main(String[] args) {
		SpringApplication.run(PartnerServiceApplicationTest.class, args);
	}
}
