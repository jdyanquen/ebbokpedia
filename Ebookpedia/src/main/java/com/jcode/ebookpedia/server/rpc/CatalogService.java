package com.jcode.ebookpedia.server.rpc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.jcode.ebookpedia.server.dao.CategoryDAO;
import com.jcode.ebookpedia.server.dao.FileTypeDAO;
import com.jcode.ebookpedia.server.dao.LanguageDAO;
import com.jcode.ebookpedia.server.model.Category;
import com.jcode.ebookpedia.server.model.FileType;
import com.jcode.ebookpedia.server.model.Language;

@Service("catalogService")
public class CatalogService {
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private FileTypeDAO fileTypeDAO;
	
	@Autowired
	private LanguageDAO languageDAO;
	
	
	@Cacheable(value = "addresses")
    public List<Category> getCategories() {
        return categoryDAO.findAll();
    }
	
	@Cacheable(value = "fileTypes")
    public List<FileType> getFileTypes() {
        return fileTypeDAO.findAll();
    }
	
	@Cacheable(value = "languages")
    public List<Language> getLanguages() {
        return languageDAO.findAll();
    }

}
