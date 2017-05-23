package org.ffsc.ssp.service.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "SLA_CONFIGURATION")
public class SLAConfiguration implements DomainEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SLA_CONFIG_ID")
	private Long id;

	@JoinColumn(name = "PRIORITY_ID", nullable = false)
	private Priority priority;

	@Column(name = "WARNING_TIME")
	private Long warningTime;

	@Column(name = "LIMIT_TIME")
	private Long limitTime;

	@Column(name = "SHOULD_ALERT_RESPONSIBLE")
	private boolean shouldAlertResponsible;

	@Column(name = "SHOULD_ALERT_MANAGER")
	private boolean shouldAlertManager;

	public Long getId() {
		return id;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Long getWarningTime() {
		return warningTime;
	}

	public void setWarningTime(Long warningTime) {
		this.warningTime = warningTime;
	}

	public Long getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Long limitTime) {
		this.limitTime = limitTime;
	}

	public boolean isShouldAlertResponsible() {
		return shouldAlertResponsible;
	}

	public void setShouldAlertResponsible(boolean shouldAlertResponsible) {
		this.shouldAlertResponsible = shouldAlertResponsible;
	}

	public boolean isShouldAlertManager() {
		return shouldAlertManager;
	}

	public void setShouldAlertManager(boolean shouldAlertManager) {
		this.shouldAlertManager = shouldAlertManager;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SLAConfiguration other = (SLAConfiguration) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}