package com.jcode.ebookpedia;

import com.jcode.ebookpedia.client.EbookpediaTest;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class EbookpediaSuite extends GWTTestSuite {
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for Ebookpedia");
		suite.addTestSuite(EbookpediaTest.class);
		return suite;
	}
}
