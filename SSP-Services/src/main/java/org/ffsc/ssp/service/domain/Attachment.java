package org.ffsc.ssp.service.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ATTACHMENT")
public class Attachment implements DomainEntity {

	private static final long serialVersionUID = 1L;

	private static final int MAX_FILE_SIZE = 16777215;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ATTACHMENT_ID", nullable = false)
	private Long id;

	@Column(name = "FILENAME", nullable = false)
	private String filename;

	@Column(length = MAX_FILE_SIZE)
	@Basic(fetch = FetchType.LAZY)
	private byte[] stream;

	@Column(name = "TICKET_ID")
	private Long ticketId;

	@Column(name = "TICKET_STEP_ID")
	private Long ticketStepId;

	@Override
	public Long getId() {
		return id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getSize() {
		return Long.valueOf(getStream().length);
	}

	public byte[] getStream() {
		return stream;
	}

	public void setStream(byte[] stream) {
		this.stream = stream;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Long getTicketStepId() {
		return ticketStepId;
	}

	public void setTicketStepId(Long ticketStepId) {
		this.ticketStepId = ticketStepId;
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
		Attachment other = (Attachment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}