package org.ffsc.ssp.service.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@AttributeOverrides({
		@AttributeOverride(name = "id", column = @Column(name = "REQUESTER_ID")),
		@AttributeOverride(name = "status", column = @Column(name = "REQUESTER_STATUS")) })
public class Requester extends User {

	private static final long serialVersionUID = 1L;

}