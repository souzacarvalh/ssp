package org.ffsc.ssp.service;

import java.util.Collection;

import org.ffsc.ssp.service.domain.SLAConfiguration;

public interface SLAService extends LocalService {

	Long saveSLAConfig(SLAConfiguration sla);

	void deleteSLAConfig(SLAConfiguration sla);

	SLAConfiguration getSLAConfig(Long id);

	Collection<SLAConfiguration> getSLAConfigs();

}