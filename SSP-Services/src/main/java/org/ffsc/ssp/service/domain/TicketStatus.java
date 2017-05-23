package org.ffsc.ssp.service.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TICKET_STATUS")
public class TicketStatus implements DomainEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TICKET_STATUS_ID", nullable = false)
	private Long id;

	@Column(nullable = false)
	private String name;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		TicketStatus other = (TicketStatus) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public enum DefaultStatus {

		OPENED("Opened"), IN_ANALYSIS("In Analysis"), WAITING_RESPONSE(
				"Waiting Response"), RESOLVED("Resolved"), CANCELED("Canceled");

		final String label;

		private DefaultStatus(String label) {
			this.label = label;
		}

		public String getName() {
			return this.label;
		}

		public static DefaultStatus getByLabel(String label) {
			for (DefaultStatus status : values()) {
				if (status.getName().equals(label)) {
					return status;
				}
			}
			return null;
		}
	}
}