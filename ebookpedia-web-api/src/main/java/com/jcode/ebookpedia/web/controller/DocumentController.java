package com.jcode.ebookpedia.web.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcode.ebookpedia.web.model.Document;

@RestController
public class DocumentController {

	@RequestMapping("/")
    public String welcome() {
        return "Welcome to RestTemplate Example.";
    }
 
    @RequestMapping("/document/{id}")
    public Document message(@PathVariable Long id) {
 
    	Document document = new Document();
    	document.setId(id);
        return document;
    }
}
