package org.ffsc.ssp.service.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "ANALYST_ID")),
		@AttributeOverride(name = "status", column = @Column(name = "ANALYST_STATUS")) })
public class Analyst extends User {

	private static final long serialVersionUID = 1L;

	@Column(name = "EMPLOYEE_ID")
	private String employeeId;

	@ManyToOne
	@JoinColumn(name = "MANAGER_ID")
	@Basic(fetch = FetchType.LAZY)
	private Analyst manager;

	@ManyToOne
	@JoinColumn(name = "GROUP_ID", nullable = false)
	private Group group;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Analyst getManager() {
		return manager;
	}

	public void setManager(Analyst manager) {
		this.manager = manager;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
}