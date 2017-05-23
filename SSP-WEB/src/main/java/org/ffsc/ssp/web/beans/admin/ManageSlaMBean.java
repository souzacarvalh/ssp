package org.ffsc.ssp.web.beans.admin;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.ffsc.ssp.service.PriorityService;
import org.ffsc.ssp.service.SLAService;
import org.ffsc.ssp.service.domain.Priority;
import org.ffsc.ssp.service.domain.SLAConfiguration;
import org.ffsc.ssp.service.exception.SSPBusinessException;
import org.ffsc.ssp.web.util.FacesContextHelper;
import org.ffsc.ssp.web.util.MessageProvider;

import com.sun.faces.context.flash.ELFlash;

@ManagedBean(name = "slaConfigMBean")
@ViewScoped
public class ManageSlaMBean {
	
	private MessageProvider messageProvider;
		
	@EJB
	private SLAService slaService;
	
	@EJB
	private PriorityService priorityService;
	
	private SLAConfiguration slaConfig = new SLAConfiguration();
	
	private List<SLAConfiguration> availableSLAConfigs = new ArrayList<>();
	private List<Priority> availablePriorities = new ArrayList<>();
	
	public void preRenderEvents(){
		try {
			
			setAvailableSLAConfigs((List<SLAConfiguration>) slaService.getSLAConfigs());
			setAvailablePriorities((List<Priority>) priorityService.getAvailablePriorities());
		
		} catch (SSPBusinessException e) {
			e.printStackTrace();
		}

		if(ELFlash.getFlash().get("slaConfigAddedMsg") != null){
			String header  = messageProvider.getValue("ssp_message_operation_done");
			String message = (String) ELFlash.getFlash().get("slaConfigAddedMsg");
			
			FacesContextHelper.addGlobalMessage(FacesMessage.SEVERITY_INFO, header, message);
		}
	}
	
	public SLAConfiguration getSlaConfiguration() {
		return slaConfig;
	}

	public void setSlaConfiguration(SLAConfiguration slaConfig) {
		this.slaConfig = slaConfig;
	}

	public List<SLAConfiguration> getAvailableSLAConfigs() {
		return availableSLAConfigs;
	}

	public void setAvailableSLAConfigs(List<SLAConfiguration> slaConfigs) {
		this.availableSLAConfigs = slaConfigs;
	}

	public List<Priority> getAvailablePriorities() {
		return availablePriorities;
	}

	public void setAvailablePriorities(List<Priority> priorities) {
		this.availablePriorities = priorities;
	}
	
	public String getEmptyTableMessage() {
		return messageProvider.getValue("ssp_admin_sla_empty_table");
	}
	
	public String saveConfig(){
		try {
			
			if(slaConfig != null){
				
				slaService.saveSLAConfig(slaConfig);

				ELFlash.getFlash().put("slaConfigAddedMsg", 
						messageProvider.getValue("ssp_message_admin_sla_save_success", slaConfig.getId()));
				
				slaConfig = new SLAConfiguration();
				
				setAvailableSLAConfigs((List<SLAConfiguration>) slaService.getSLAConfigs());
			}
			
		} catch (SSPBusinessException e) {
			FacesContextHelper.showMessageDialog(FacesMessage.SEVERITY_ERROR, 
					messageProvider.getValue("ssp_message_admin_sla_save_fail"));
		}
		
		return "sla.jsf?faces-redirect=true";
	}
	
	public void deleteConfig(SLAConfiguration slaConfig){
		try {
			
			if(slaConfig != null){
				
				Long id = slaConfig.getId();
				
				slaService.deleteSLAConfig(slaConfig);
				setAvailableSLAConfigs((List<SLAConfiguration>) slaService.getSLAConfigs());

				String header  = messageProvider.getValue("ssp_message_operation_done");
				String message = messageProvider.getValue("ssp_message_admin_sla_delete_success", id);
				
				FacesContextHelper.addGlobalMessage(FacesMessage.SEVERITY_INFO, header, message);
			}
			
		} catch (SSPBusinessException e) {
			FacesContextHelper.showMessageDialog(FacesMessage.SEVERITY_ERROR, 
					messageProvider.getValue("ssp_message_admin_SLA_delete_fail"));
		}
	}
	
	public void reset(){	
		try {
			
			setAvailableSLAConfigs((List<SLAConfiguration>) slaService.getSLAConfigs());
			setAvailablePriorities((List<Priority>) priorityService.getAvailablePriorities());
			
			slaConfig = new SLAConfiguration();
		
		} catch (SSPBusinessException e) {
			e.printStackTrace();
		}
	}
	
	@Inject
	public void setMessageProvider(MessageProvider messageProvider) {
		this.messageProvider = messageProvider;
	}
}