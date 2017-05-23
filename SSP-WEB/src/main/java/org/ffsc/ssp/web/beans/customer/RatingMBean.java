package org.ffsc.ssp.web.beans.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.ffsc.ssp.service.TreatmentService;
import org.ffsc.ssp.service.domain.Treatment;
import org.ffsc.ssp.service.exception.SSPBusinessException;
import org.ffsc.ssp.web.beans.common.Navigation;
import org.ffsc.ssp.web.beans.common.SSPMessage;
import org.ffsc.ssp.web.util.FacesContextHelper;
import org.ffsc.ssp.web.util.MessageProvider;

import com.sun.faces.context.flash.ELFlash;

@ManagedBean
@ViewScoped
public class RatingMBean {

	private MessageProvider messageProvider;

	private List<Treatment> treatmensReadyForRating = new ArrayList<>();

	private TreatmentService treatmentService;

	@PostConstruct
	public void initialize() {
		treatmensReadyForRating.addAll(treatmentService.getTreatmentsReadyForRating());
	}
	
	public void preRenderEvents() {
		if(ELFlash.getFlash().get("feedbackRegistered") != null){
			String header  = messageProvider.getValue(SSPMessage.SAVE_FEEDBACK_SUCCESS);
			String message = messageProvider.getValue(SSPMessage.SAVE_FEEDBACK_DETAIL);
			FacesContextHelper.addGlobalMessage(FacesMessage.SEVERITY_INFO, header, message);
		}
	}

	public List<Treatment> getTreatmensReadyForRating() {
		return treatmensReadyForRating;
	}

	public void setTreatmensReadyForRating(List<Treatment> treatments) {
		this.treatmensReadyForRating = treatments;
	}

	public String rateIt(Treatment treatment) {
		try {
			
			if(treatment != null){
				
				treatment.getRating().setSubmittedOn(new Date(System.currentTimeMillis()));
				treatmentService.updateTreatment(treatment);
				
				ELFlash.getFlash().put("feedbackRegistered", true);
			}
						
		} catch (SSPBusinessException e) {
			FacesContextHelper.showMessageDialog(FacesMessage.SEVERITY_ERROR, 
					messageProvider.getValue(SSPMessage.SAVE_FEEDBACK_FAIL));
		}
		
		return Navigation.RATING_PAGE_REDIRECT.getPath();
	}
	
	@EJB
	public void setTreatmentService(TreatmentService treatmentService) {
		this.treatmentService = treatmentService;
	}
	
	@Inject
	public void setMessageProvider(MessageProvider messageProvider) {
		this.messageProvider = messageProvider;
	}
}