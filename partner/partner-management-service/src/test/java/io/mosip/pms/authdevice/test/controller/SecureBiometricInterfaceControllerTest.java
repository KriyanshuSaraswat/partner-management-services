package io.mosip.pms.authdevice.test.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.mosip.pms.common.constant.Purpose;
import io.mosip.pms.common.dto.PageResponseDto;
import io.mosip.pms.common.dto.Pagination;
import io.mosip.pms.common.dto.SearchFilter;
import io.mosip.pms.common.dto.SearchSort;
import io.mosip.pms.common.request.dto.RequestWrapper;
import io.mosip.pms.common.response.dto.ResponseWrapper;
import io.mosip.pms.device.authdevice.service.SecureBiometricInterfaceService;
import io.mosip.pms.device.regdevice.service.RegSecureBiometricInterfaceService;
import io.mosip.pms.device.request.dto.DeviceSearchDto;
import io.mosip.pms.device.request.dto.SecureBiometricInterfaceCreateDto;
import io.mosip.pms.device.request.dto.SecureBiometricInterfaceStatusUpdateDto;
import io.mosip.pms.device.request.dto.SecureBiometricInterfaceUpdateDto;
import io.mosip.pms.device.response.dto.IdDto;
import io.mosip.pms.device.response.dto.SbiSearchResponseDto;
import io.mosip.pms.device.util.AuditUtil;
import io.mosip.pms.partner.test.PartnerServiceApplicationTest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PartnerServiceApplicationTest.class)
@AutoConfigureMockMvc
@EnableWebMvc

public class SecureBiometricInterfaceControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
   
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
	AuditUtil auditUtil;
	
    @MockBean	
   private  SecureBiometricInterfaceService secureBiometricInterfaceService;
	
    @MockBean	
   private  RegSecureBiometricInterfaceService regSecureBiometricInterface;
    
    RequestWrapper<SecureBiometricInterfaceCreateDto> createRequest=null;
    RequestWrapper<SecureBiometricInterfaceUpdateDto> updateRequest=null;
    
    @Before
    public void setup() {
    	Mockito.doNothing().when(auditUtil).auditRequest(any(), any(), any());
    	Mockito.doNothing().when(auditUtil).auditRequest(any(), any(), any(),any());
    	PageResponseDto<SbiSearchResponseDto> searchresponse = new PageResponseDto<SbiSearchResponseDto>();
    	IdDto response = new IdDto();
    	ResponseWrapper<IdDto> responseWrapper = new ResponseWrapper<>();
    	ResponseWrapper<PageResponseDto<SbiSearchResponseDto>> searchResponseWrapper = new ResponseWrapper<>();
    	searchResponseWrapper.setResponse(searchresponse);
    	responseWrapper.setResponse(response);
    	Mockito.when(regSecureBiometricInterface.searchSecureBiometricInterface(Mockito.any(), Mockito.any())).thenReturn(searchresponse);
        Mockito.when(regSecureBiometricInterface.updateSecureBiometricInterface(Mockito.any())).thenReturn(response);
        Mockito.when(regSecureBiometricInterface.createSecureBiometricInterface(Mockito.any())).thenReturn(response);
        Mockito.when(secureBiometricInterfaceService.searchSecureBiometricInterface(Mockito.any(), Mockito.any())).thenReturn(searchresponse);
        Mockito.when(secureBiometricInterfaceService.updateSecureBiometricInterface(Mockito.any())).thenReturn(response);
        Mockito.when(secureBiometricInterfaceService.createSecureBiometricInterface(Mockito.any())).thenReturn(response);
        createRequest = createRequest(false);
        updateRequest=updateRequest(false);
    }
    
    private RequestWrapper<DeviceSearchDto> searchRequest() {
    	RequestWrapper<DeviceSearchDto> request = new RequestWrapper<DeviceSearchDto>();
        request.setRequest(searchSBIRequest());
        request.setId("mosip.partnermanagement.sbi.update");
        request.setVersion("1.0");
        request.setRequesttime(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        request.setMetadata("{}");
        return request;
	}
    
    private DeviceSearchDto searchSBIRequest () {
    	DeviceSearchDto dto = new DeviceSearchDto();
    	Pagination pagination = new Pagination();
    	SearchSort searchSort = new SearchSort();
    	SearchFilter searchFilter = new SearchFilter();
    	searchSort.setSortField("model");
    	searchSort.setSortType("asc");
    	searchFilter.setColumnName("model");
    	searchFilter.setFromValue("");
    	searchFilter.setToValue("");
    	searchFilter.setType("STARTSWITH");
    	searchFilter.setValue("b");
    	List<SearchSort> searchDtos1 = new ArrayList<SearchSort>();
    	searchDtos1.add(searchSort);
    	List<SearchFilter> searchfilterDtos = new ArrayList<SearchFilter>();
    	searchfilterDtos.add(searchFilter);
    	pagination.setPageFetch(10);
    	pagination.setPageStart(0);
    	dto.setFilters(searchfilterDtos);
    	dto.setPagination(pagination);
    	dto.setPurpose(Purpose.AUTH);
    	dto.setSort(searchDtos1);
    	return dto;
    }
    
    private RequestWrapper<DeviceSearchDto> searchRegRequest() {
    	RequestWrapper<DeviceSearchDto> request = new RequestWrapper<DeviceSearchDto>();
        request.setRequest(searchRegSBIRequest());
        request.setId("mosip.partnermanagement.sbi.update");
        request.setVersion("1.0");
        request.setRequesttime(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        request.setMetadata("{}");
        return request;
	}
    
    private DeviceSearchDto searchRegSBIRequest () {
    	DeviceSearchDto dto = new DeviceSearchDto();
    	Pagination pagination = new Pagination();
    	SearchSort searchSort = new SearchSort();
    	SearchFilter searchFilter = new SearchFilter();
    	searchSort.setSortField("model");
    	searchSort.setSortType("asc");
    	searchFilter.setColumnName("model");
    	searchFilter.setFromValue("");
    	searchFilter.setToValue("");
    	searchFilter.setType("STARTSWITH");
    	searchFilter.setValue("b");
    	List<SearchSort> searchDtos1 = new ArrayList<SearchSort>();
    	searchDtos1.add(searchSort);
    	List<SearchFilter> searchfilterDtos = new ArrayList<SearchFilter>();
    	searchfilterDtos.add(searchFilter);
    	pagination.setPageFetch(10);
    	pagination.setPageStart(0);
    	dto.setFilters(searchfilterDtos);
    	dto.setPagination(pagination);
    	dto.setPurpose(Purpose.REGISTRATION);
    	dto.setSort(searchDtos1);
    	return dto;
    }
    
    private RequestWrapper<SecureBiometricInterfaceUpdateDto> updateRequest(boolean isItForRegistrationDevice) {
    	RequestWrapper<SecureBiometricInterfaceUpdateDto> request = new RequestWrapper<SecureBiometricInterfaceUpdateDto>();
        request.setRequest(createSBIUpdate(isItForRegistrationDevice));
        request.setId("mosip.partnermanagement.sbi.update");
        request.setVersion("1.0");
        request.setRequesttime(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        request.setMetadata("{}");
        return request;
	}

	private SecureBiometricInterfaceUpdateDto createSBIUpdate(boolean isItForRegistrationDevice) {
		SecureBiometricInterfaceUpdateDto sbidto = new SecureBiometricInterfaceUpdateDto();
    	
    	sbidto.setDeviceDetailId("1234");
    	sbidto.setSwBinaryHash("swb");
    	sbidto.setSwCreateDateTime(LocalDateTime.now());
    	sbidto.setSwExpiryDateTime(LocalDateTime.now());
    	sbidto.setIsActive(true);
    	sbidto.setIsItForRegistrationDevice(isItForRegistrationDevice);
    	sbidto.setSwVersion("v1");
    	sbidto.setId("1234");
        return sbidto;
	}

	private RequestWrapper<SecureBiometricInterfaceCreateDto> createRequest(boolean isItForRegistrationDevice) {
        RequestWrapper<SecureBiometricInterfaceCreateDto> request = new RequestWrapper<SecureBiometricInterfaceCreateDto>();
        request.setRequest(createSBIRequest(isItForRegistrationDevice));
        request.setId("mosip.partnermanagement.sbi.create");
        request.setVersion("1.0");
        request.setRequesttime(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        request.setMetadata("{}");
        return request;
    }
    
    private SecureBiometricInterfaceCreateDto createSBIRequest(boolean isItForRegistrationDevice) {
    	SecureBiometricInterfaceCreateDto sbidto = new SecureBiometricInterfaceCreateDto();
    	
    	sbidto.setDeviceDetailId("1234");
    	sbidto.setSwBinaryHash("swb");
    	sbidto.setSwCreateDateTime(LocalDateTime.now());
    	sbidto.setSwExpiryDateTime(LocalDateTime.now());
    	
    	sbidto.setIsItForRegistrationDevice(isItForRegistrationDevice);
    	sbidto.setSwVersion("v1");
        
        return sbidto;
    }
     
    private RequestWrapper<SecureBiometricInterfaceStatusUpdateDto>approvalRequest(boolean isItForRegistrationDevice) {
        RequestWrapper<SecureBiometricInterfaceStatusUpdateDto> request = new RequestWrapper<SecureBiometricInterfaceStatusUpdateDto>();
        request.setRequest(approvalDetailRequest(isItForRegistrationDevice));
        request.setId("mosip.partnermanagement.devicedetail.create");
        request.setVersion("1.0");
        request.setRequesttime(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime());
        request.setMetadata("{}");
        return request;
    }
    
    private SecureBiometricInterfaceStatusUpdateDto approvalDetailRequest(boolean isItForRegistrationDevice) {
    	SecureBiometricInterfaceStatusUpdateDto dto = new SecureBiometricInterfaceStatusUpdateDto();
    	dto.setApprovalStatus("Activate");
    	dto.setId("123456");
    	dto.setIsItForRegistrationDevice(isItForRegistrationDevice);
    	return dto;
    }
    
    @Test
    @WithMockUser(roles = {"DEVICE_PROVIDER"})
    public void createsbiTest() throws Exception {      

        mockMvc.perform(post("/securebiometricinterface").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(createRequest))).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"DEVICE_PROVIDER"})
    public void updatesbiTest() throws Exception {
    	mockMvc.perform(put("/securebiometricinterface").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(updateRequest))).andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(roles = {"PARTNER_ADMIN"})
    public void approveDeviceDetailsTest() throws JsonProcessingException, Exception {
    	RequestWrapper<SecureBiometricInterfaceStatusUpdateDto> createrequest=approvalRequest(false);
    	mockMvc.perform(MockMvcRequestBuilders.patch("/securebiometricinterface").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(createrequest))).andExpect(status().isOk());    	
    }
    
    @Test
    @WithMockUser(roles = {"DEVICE_PROVIDER"})
    public void searchSecureBiometricTest() throws JsonProcessingException, Exception {
    	RequestWrapper<DeviceSearchDto> createrequest=searchRequest();
    	mockMvc.perform(MockMvcRequestBuilders.post("/securebiometricinterface/search").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(createrequest))).andExpect(status().isOk());    	
    }
    
    @Test
    @WithMockUser(roles = {"PARTNER_ADMIN"})
    public void approveDeviceDetailsTest_regDevice() throws JsonProcessingException, Exception {
    	RequestWrapper<SecureBiometricInterfaceStatusUpdateDto> createrequest=approvalRequest(true);
    	mockMvc.perform(MockMvcRequestBuilders.patch("/securebiometricinterface").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(createrequest))).andExpect(status().isOk());    	
    }
    
    @Test
    @WithMockUser(roles = {"DEVICE_PROVIDER"})
    public void createsbiTest_regDevice() throws Exception {
    	RequestWrapper<SecureBiometricInterfaceCreateDto> createRequest=createRequest(true);
        mockMvc.perform(post("/securebiometricinterface").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(createRequest))).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"DEVICE_PROVIDER"})
    public void updatesbiTest_regDevice() throws Exception {
    	RequestWrapper<SecureBiometricInterfaceUpdateDto> updateRequest=updateRequest(true);
    	mockMvc.perform(put("/securebiometricinterface").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(updateRequest))).andExpect(status().isOk());
    }
    
    @Test
    @WithMockUser(roles = {"DEVICE_PROVIDER"})
    public void searchRegSecureBiometricTest() throws JsonProcessingException, Exception {
    	RequestWrapper<DeviceSearchDto> createrequest=searchRegRequest();
    	mockMvc.perform(MockMvcRequestBuilders.post("/securebiometricinterface/search").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(createrequest))).andExpect(status().isOk());    	
    }
}