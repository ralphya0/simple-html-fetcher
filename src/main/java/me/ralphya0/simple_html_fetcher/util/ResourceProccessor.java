package me.ralphya0.simple_html_fetcher.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import javax.swing.JTextArea;

import org.htmlparser.util.ParserException;

public interface ResourceProccessor {
	//load and save page resources like css
	
	public void pictureFilter(String siteDir);
	public void styleFilter(Map<String,String> pageMapping,String fileBase,JTextArea ta) throws ParserException, MalformedURLException, IOException;
}
