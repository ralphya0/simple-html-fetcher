package me.ralphya0.simple_html_fetcher.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

import org.htmlparser.util.ParserException;

public interface LinkParser {
	//parse http links in web page
	
	
	//argument namespace is used to define the range of pages to be fetched
	public List<String> parseLink(Map<String,String> pageMapping,String namespace,String fileBase,JTextArea ta) throws ParserException, IOException;
	public final static String C = System.getProperty("line.separator");
	
	public long timeSpending();
}
