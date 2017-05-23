package org.ffsc.ssp.web.infrastructure;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class SSPPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent pe) {
		
	}

	@Override
	public void beforePhase(PhaseEvent pe) {
				
	}

	@Override
	public PhaseId getPhaseId() {	
		return PhaseId.ANY_PHASE;
	}
}