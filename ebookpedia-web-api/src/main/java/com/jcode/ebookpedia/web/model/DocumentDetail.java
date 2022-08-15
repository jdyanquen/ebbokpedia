package com.jcode.ebookpedia.web.model;


import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "DOCUMENT_DETAILS")
public class DocumentDetail implements BaseBean {

	/** Serial Version UUID */
	private static final long serialVersionUID = 1L;

	// Properties

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_details_id_seq")
	@SequenceGenerator(name = "document_details_id_seq", sequenceName = "document_details_id_seq", allocationSize = 1)
	private Long id;
	
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		return obj instanceof DocumentDetail && Objects.equals(this.getId(), ((DocumentDetail)obj).getId());
	}

}
