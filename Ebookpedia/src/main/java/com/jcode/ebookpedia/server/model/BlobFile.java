package com.jcode.ebookpedia.server.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blob_file")
public class BlobFile implements Identifiable {
	
	@Id
	private Long id;

	private String name;

	private String contentType;

	private byte[] content;

}
