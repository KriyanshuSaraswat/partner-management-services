package io.mosip.pms.device.request.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class DeviceDetailSearchDto extends DeviceSearchDto {
	
	private String deviceProviderId;
}
