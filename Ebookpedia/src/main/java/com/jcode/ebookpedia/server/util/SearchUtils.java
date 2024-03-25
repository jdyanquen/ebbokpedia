package com.jcode.ebookpedia.server.util;

import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.snowball.SnowballAnalyzer;

public class SearchUtils {

	private static final Logger log = Logger.getLogger(SearchUtils.class
			.getName());

	/** From Stop Analyzer Lucene 2.9.1 en-es */
	public final static String[] stopWords = new String[] { "a", "an", "and",
			"are", "as", "at", "be", "but", "by", "for", "if", "in", "into",
			"is", "it", "no", "not", "of", "on", "or", "such", "that", "the",
			"their", "then", "there", "these", "they", "this", "to", "was",
			"will", "with", "what", "when", "where", "who", "why", "how", "un",
			"una", "uno", "y", "e", "o", "u", "son", "como", "pero", "por",
			"para", "de", "del", "si", "sin", "con", "en", "es", "entre",
			"esto", "estos", "sobre", "tal", "fué", "su", "sus", "que", "más",
			"menos", "la", "las", "los", "el", "null" };

	/**
	 * Uses english stemming (snowball + lucene) + stopwords for getting the
	 * words.
	 */
	@SuppressWarnings("deprecation")
	public static Set<String> parseSearch(String str,
			int maximumNumberOfTokensToReturn) {

		Set<String> keywords = new HashSet<String>();
		if (str == null)
			str = "";

		try {
			Analyzer analyzer = new SnowballAnalyzer(
					org.apache.lucene.util.Version.LUCENE_CURRENT, "English",
					stopWords);

			String indexCleanedOfHTMLTags = str.replaceAll("\\<.*?>", " ");
			TokenStream tokenStream = analyzer.tokenStream("",
					new StringReader(indexCleanedOfHTMLTags));
			Token token = new Token();

			while (((token = tokenStream.next()) != null)
					&& (keywords.size() < maximumNumberOfTokensToReturn)) {

				keywords.add(token.term());
			}

		} catch (Exception e) {
			log.severe(e.getMessage());
		}
		return keywords;
	}

}
