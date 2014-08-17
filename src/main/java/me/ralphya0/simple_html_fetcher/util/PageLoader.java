package me.ralphya0.simple_html_fetcher.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

import org.htmlparser.util.ParserException;

public interface PageLoader {
	//load and save html pages 
	
	
	public static Map<String , String > srcMapping = new HashMap<String,String>();
	public static Map<String , String > pageMapping = new HashMap<String,String>();
	public static List<String> urlsToSearch = new ArrayList<String>();
	
	public long timeSpending();
	
	public Map<String,String> savePage(List<String> urlList,String fileBase,JTextArea ta) throws ParserException, IOException;
	public boolean tasksComplete();
}
