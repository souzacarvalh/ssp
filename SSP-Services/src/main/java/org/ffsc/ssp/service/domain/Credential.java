package org.ffsc.ssp.service.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Credential implements DomainEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(updatable = false, nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIRATION_DATE")
	private Date expirationDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CredentialStatus status;

	@Enumerated(EnumType.STRING)
	@Column(name = "ACCESS_TYPE")
	private AccessType accessType;

	@OneToOne(optional = true, mappedBy = "credential")
	private Requester requester;

	@OneToOne(optional = true, mappedBy = "credential")
	private Analyst analyst;

	@Override
	public Long getId() {
		return 0L;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public CredentialStatus getStatus() {
		return status;
	}

	public void setStatus(CredentialStatus status) {
		this.status = status;
	}

	public AccessType getAccessType() {
		return accessType;
	}

	public void setAccessType(AccessType accessType) {
		this.accessType = accessType;
	}

	public Requester getRequester() {
		return requester;
	}

	public void setRequester(Requester requester) {
		this.requester = requester;
	}

	public Analyst getAnalyst() {
		return analyst;
	}

	public void setAnalyst(Analyst analyst) {
		this.analyst = analyst;
	}
	
	public User getUser() {
		return requester != null ? requester : analyst;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		Credential other = (Credential) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public enum AccessType {
		ADMINISTRATION, SUPPORT, CUSTOMER;
	}

	public enum CredentialStatus {
		ACTIVE, SUSPENDED, CANCELED;
	}
}