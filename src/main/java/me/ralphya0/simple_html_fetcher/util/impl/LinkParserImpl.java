package me.ralphya0.simple_html_fetcher.util.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

import me.ralphya0.simple_html_fetcher.util.LinkParser;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


public class LinkParserImpl implements LinkParser{
	static boolean statusCheck = false;
	public static long proccessingTime = 0;
	

	@Override
	public List<String> parseLink(Map<String, String> pageMapping,String namespace,String fileBase,JTextArea ta) throws ParserException, IOException {
		List<String> re = new ArrayList<String>();
		Date begin = new Date();
		
		String[] keySet =  pageMapping.keySet().toArray(new String[0]);
		int length = namespace.length();
		int index = 0;
		String fileName = "";
		String temp = "";
		String test = "";
		String urlTemp = "";
	
		while(index < keySet.length){
			
			//parse newly saved html page
			fileName = pageMapping.get(keySet[index]);
			
			if(fileName != null && fileName.length() > 0){
				//System.out.println("正在解析本地页面："+fileBase+fileName);
				Parser p = new Parser(fileBase + fileName);
				NodeList list = p.parse(null);
				StringBuffer sb = new StringBuffer();
				
				for(int i = 0;i < list.size();i++){
					
					test = "";
					temp = "";
					urlTemp = "";
					Node nd = list.elementAt(i);
					test = nd.toHtml().trim();
					
					if(test.length() > 0){
						List<Node> array = new ArrayList<Node>();
						
						
						if(nd instanceof LinkTag){
							LinkTag lt = (LinkTag)nd;
							urlTemp = lt.extractLink();
							
							
							if(urlTemp.length() > 0){
								if(urlTemp.indexOf("#") == -1){
									
									if(urlTemp.indexOf(namespace) != -1){
										if(pageMapping.get(urlTemp) != null){
											//the page pointed by this link had been saved locally
											lt.setLink("./"+pageMapping.get(urlTemp));
										}
										else{
											//adding the new link into waiting queue which will be processed later
											re.add(urlTemp);
										}
									}
									else{
										//just ignore those links outside our namespace
										lt.setLink(null);
									}
								}
								else{
									lt.setLink(null);
								}
						}
						}
						
						//deal with child nodes
						NodeList cl = nd.getChildren();
						
						if(cl != null && cl.size() > 0){
							for(int j = 0;j < cl.size();j++){
								test = "";
								test = cl.elementAt(j).toHtml().trim();
								if(test.length()>0){
									array.add(cl.elementAt(j));
								}
							}
							
							while(!array.isEmpty()){
								Node ts = array.get(0);
								array.remove(0);
								if(ts instanceof LinkTag){
									LinkTag tag = (LinkTag)ts;
									urlTemp = tag.extractLink();
									if(!urlTemp.equals("") && urlTemp.length()>0){
										
									
									if(urlTemp.indexOf("#")==-1){
										
										if(urlTemp.indexOf(namespace) != -1){
											if(pageMapping.get(urlTemp) != null){
												tag.setLink("./"+pageMapping.get(urlTemp));
											}
											else{
												re.add(urlTemp);
											}
										}
										else if(urlTemp.substring(0, 2).equals("./"))	{
											
										}
										else{
											tag.setLink(null);
										}
									}
									else{
										tag.setLink(null);
									}
								}
								}
								
								NodeList nl = ts.getChildren();
								if(nl!=null&&nl.size()>0){
									for(int k=0;k<nl.size();k++){
										test = "";

										test = nl.elementAt(k).toHtml().trim();
										if(!test.equals("") && test.length() > 0){
											
											array.add(nl.elementAt(k));
										}
									}
								}
							}
						}
						sb.append(nd.toHtml() + C);
					}
				}
				
			
				BufferedWriter bf = new BufferedWriter(new FileWriter(fileBase + fileName));
				bf.write(sb.toString());
				bf.close();
				
			}
			index++;
			
		}
		Date end = new Date();
		proccessingTime += (end.getTime() - begin.getTime()) / 1000;
		return re;
	}
	@Override
	public long timeSpending() {
		// TODO Auto-generated method stub
		return proccessingTime;
	}

}


