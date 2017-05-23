package org.ffsc.ssp.service.impl;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ffsc.ssp.service.GroupService;
import org.ffsc.ssp.service.domain.Group;
import org.ffsc.ssp.service.persistence.GroupDAO;

@Stateless
public class GroupServiceBean implements GroupService {

	private static final long serialVersionUID = 1L;

	private GroupDAO groupDAO;
	
	@Inject
	public GroupServiceBean(GroupDAO daoImpl) {
		this.groupDAO = daoImpl;
	}
	
	@Override
	public Group getGroup(Long id){
		return groupDAO.byId(id);
	}
	
	@Override
	public Collection<Group> getAvailableGroups(){
		return groupDAO.listAll();
	}
}