package org.ffsc.ssp.service.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Route implements DomainEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROUTE_ID")
	private Long id;

	@ManyToOne(optional = true)
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;

	@ManyToOne(optional = true)
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;

	@ManyToOne(optional = true)
	@JoinColumn(name = "GROUP_ID")
	private Group supportGroup;

	@ManyToOne(optional = true)
	@JoinColumn(name = "ANALYST_ID")
	private Analyst supportAnalyst;

	public Long getId() {
		return id;
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
		Route other = (Route) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}