package org.ffsc.ssp.service.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SUPPORT_GROUP")
public class Group implements DomainEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GROUP_ID", nullable = false)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(name = "MANAGMENT_GROUP")
	private boolean managmentGroup;

	@Column(name = "RECEIVE_NOT_ROUTED")
	private boolean receiveNotRouted;

	@Enumerated(EnumType.STRING)
	@Column(name = "GROUP_STATUS", nullable = false)
	private RecordStatus status;

	@Override
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isManagmentGroup() {
		return managmentGroup;
	}

	public void setManagmentGroup(boolean managmentGroup) {
		this.managmentGroup = managmentGroup;
	}

	public boolean isReceiveNotRouted() {
		return receiveNotRouted;
	}

	public void setReceiveNotRouted(boolean receiveNotRouted) {
		this.receiveNotRouted = receiveNotRouted;
	}

	public RecordStatus getStatus() {
		return status;
	}

	public void setStatus(RecordStatus status) {
		this.status = status;
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
		Group other = (Group) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}