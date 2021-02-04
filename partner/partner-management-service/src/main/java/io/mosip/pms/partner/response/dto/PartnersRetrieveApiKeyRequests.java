package io.mosip.pms.partner.response.dto;

import java.util.List;

import lombok.Data;

/**
 * @author sanjeev.shrivastava
 *
 */

@Data
public class PartnersRetrieveApiKeyRequests {
	
	private List<APIkeyRequests> aPIkeyRequests;
}
