package com.jcode.ebookpedia;

import com.google.gwt.junit.tools.GWTTestSuite;
import com.jcode.ebookpedia.client.AppTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AppSuite extends GWTTestSuite {
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for App");
		suite.addTestSuite(AppTest.class);
		return suite;
	}
}
