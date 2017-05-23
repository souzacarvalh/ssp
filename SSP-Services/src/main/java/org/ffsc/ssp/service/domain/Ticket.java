package org.ffsc.ssp.service.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Ticket implements DomainEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TICKET_ID", nullable = false)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPEN_DATE", nullable = false, updatable = false)
	private Date openDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CLOSE_DATE")
	private Date closeDate;

	@Column(name = "CANCEL_REASON")
	private String cancelReason;

	@ManyToOne
	@JoinColumn(name = "TICKET_STATUS_ID")
	private TicketStatus status;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "PRIORITY_ID")
	private Priority priority;

	@ManyToOne
	@JoinColumn(name = "REQUESTER_ID")
	private Requester requester;

	@Column(name = "CLOSED_BY")
	private String closedBy;

	@OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
	@OrderBy(value = "date DESC")
	private List<TicketStep> ticketThread = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TICKET_ID")
	private List<Attachment> attachments = new ArrayList<>();

	@OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL)
	private Treatment treatmentInfo;

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Requester getRequester() {
		return requester;
	}

	public void setRequester(Requester requester) {
		this.requester = requester;
	}

	public String getClosedBy() {
		return closedBy;
	}

	public void setClosedBy(String closedBy) {
		this.closedBy = closedBy;
	}

	public boolean isCanceled() {
		return this.getStatus().getName()
				.equals(TicketStatus.DefaultStatus.CANCELED.getName());
	}

	public boolean isResolved() {
		return this.getStatus().getName()
				.equals(TicketStatus.DefaultStatus.RESOLVED.getName());
	}

	public boolean isClosed() {
		return this.getCloseDate() != null;
	}

	public boolean isBeingTreated() {
		return this.getTreatmentInfo() != null
				&& this.getTreatmentInfo().isActive();
	}

	public List<TicketStep> getTicketThread() {
		return ticketThread;
	}

	public List<TicketStep> getPublicThread() {

		List<TicketStep> publicSteps = new ArrayList<>();

		for (TicketStep step : getTicketThread()) {
			if (!step.isInternal()) {
				publicSteps.add(step);
			}
		}

		return publicSteps;
	}

	public List<TicketStep> getInternalThread() {

		List<TicketStep> publicSteps = new ArrayList<>();

		for (TicketStep step : getTicketThread()) {
			if (step.isInternal()) {
				publicSteps.add(step);
			}
		}

		return publicSteps;
	}

	public void addStepToTicketThread(TicketStep ticketStep) {
		this.ticketThread.add(ticketStep);
	}

	public void setTicketThread(List<TicketStep> ticketThread) {
		this.ticketThread = ticketThread;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void addAttachment(Attachment attachment) {
		this.attachments.add(attachment);
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public Treatment getTreatmentInfo() {
		return treatmentInfo;
	}

	public void setTreatmentInfo(Treatment treatmentInfo) {
		this.treatmentInfo = treatmentInfo;
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
		Ticket other = (Ticket) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", title=" + title + ", openDate="
				+ openDate + ", closeDate=" + closeDate + ", status=" + status
				+ ", product=" + product + ", category=" + category
				+ ", priority=" + priority + ", requester=" + requester + "]";
	}
}