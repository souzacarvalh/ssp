package org.ffsc.ssp.service.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TREATMENT")
public class Treatment implements DomainEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TREATMENT_ID")
	private Long id;

	@OneToOne
	@JoinColumn(name = "TICKET_ID")
	private Ticket ticket;

	@ManyToOne(optional = true)
	@JoinColumn(name = "GROUP_ID")
	private Group supportGroup;

	@ManyToOne(optional = true)
	@JoinColumn(name = "ANALYST_ID")
	private Analyst supportAnalyst;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE", nullable = false)
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE")
	private Date endDate;

	@OneToOne(optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "RATING_ID")
	private Rating rating;

	private boolean active;

	private boolean closed = false;

	@Column(name = "SHOULD_REDIRECT")
	private boolean shouldRedirect;

	public Long getId() {
		return id;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Group getSupportGroup() {
		return supportGroup;
	}

	public void setSupportGroup(Group supportGroup) {
		this.supportGroup = supportGroup;
	}

	public Analyst getSupportAnalyst() {
		return supportAnalyst;
	}

	public void setSupportAnalyst(Analyst supportAnalyst) {
		this.supportAnalyst = supportAnalyst;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public boolean isShouldRedirect() {
		return shouldRedirect;
	}

	public void setShouldRedirect(boolean shouldRedirect) {
		this.shouldRedirect = shouldRedirect;
	}

	public boolean isExceededWarningTime() {
		if (ticket != null && !ticket.isClosed()) {

			SLAConfiguration slaConfig = ticket.getPriority().getSlaConfig();

			if (slaConfig != null) {

				Date openOn = ticket.getOpenDate();

				Date targetWarningTime = new Date(openOn.getTime()
						+ (slaConfig.getWarningTime() * 60000L));

				Date now = new Date(System.currentTimeMillis());

				return now.after(targetWarningTime);
			}
		}

		return false;
	}

	public boolean isExceededLimitTime() {
		if (ticket != null && !ticket.isClosed()) {

			SLAConfiguration slaConfig = ticket.getPriority().getSlaConfig();

			if (slaConfig != null) {

				Date openOn = ticket.getOpenDate();

				Date targetLimitTime = new Date(openOn.getTime()
						+ (slaConfig.getLimitTime() * 60000L));

				Date now = new Date(System.currentTimeMillis());

				return now.after(targetLimitTime);
			}
		}

		return false;
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
		Treatment other = (Treatment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}