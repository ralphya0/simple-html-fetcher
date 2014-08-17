package me.ralphya0.simple_html_fetcher.util.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

import me.ralphya0.simple_html_fetcher.util.PageLoader;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


public class PageLoaderImpl implements PageLoader{
	public final static String C = System.getProperty("line.separator");
	static int urlIndex = 0;
	static int pageCount = 1;
	final static int siteCount = 1;
	static int imgCount = 1;
	static boolean overFlag = false;
	public static long proccessingTime = 0;
	
	@Override
	public Map<String, String> savePage(List<String> urlList,String fileBase,JTextArea ta) throws ParserException, IOException {
		Date begin = new Date();
		int ck = 0;
		if(urlList !=null && !urlList.isEmpty()){
			
			for(int i = 0;i < urlList.size();i++){
				boolean flag = false;
				if(urlList.get(i).equals("http://api.jquery.com/extending-ajax/")){
					//页面http://api.jquery.com/extending-ajax/   不存在，导致程序出现异常。。。
					continue;
				}
				if(pageMapping.get(urlList.get(i)) == null){
					for(int j = 0;j < urlsToSearch.size();j++){
						
						if(urlList.get(i).equals(urlsToSearch.get(j))){
							flag = true;
							break;
						}
					}		
				}else{
					flag = true;
					continue;
				}
				
				if(flag == false){
					//new url link!
					urlsToSearch.add(urlList.get(i));
					ck++;
				}
			}
			if(ck == 0){
				overFlag = true;
				//System.out.println("+++++++++++全部链接已处理完毕，程序即将退出！");
			}
		}
		
		//load and save new pages
		while(!urlsToSearch.isEmpty() && urlIndex < urlsToSearch.size()){
			String url = urlsToSearch.get(urlIndex);
			urlIndex++;
			System.out.println("正在抓取页面："+url);
			ta.append("正在抓取页面："+url+C);
			Parser parser = new Parser(url);
			NodeList nodeList = parser.parse(null);
			StringBuffer sb = new StringBuffer();
			String test = "";
			for(int i = 0;i < nodeList.size();i++){
				test = "";
				Node n = nodeList.elementAt(i);

				test = n.toHtml().trim();
				
				if(test != null && !test.equals("") && test.length() > 0){
					
					sb.append(n.toHtml()+C);
				}
			}
			//use a simple naming strategy : page1.html page2.html...
			String newFileName = "page" + pageCount + ".html";
			BufferedWriter bf = new BufferedWriter(new FileWriter(fileBase + newFileName));
			pageCount++;
			bf.write(sb.toString());
			bf.close();				
				
			//
			pageMapping.put(url, newFileName);
			/*if(pageMapping.get(url)==null||pageMapping.get(url).equals("")){
				overFlag = true;
				System.out.println("++++++++++++++++++pageMapping 插入值为空，程序出现异常！！！");
				return null;
			}*/
			System.out.println("页面抓取成功！("+url+")");
			ta.append("页面抓取成功！("  +url + ")" + C);
		}
		Date end = new Date();
		proccessingTime+=(end.getTime() - begin.getTime()) / 1000;
		return pageMapping;
	}
	@Override
	public boolean tasksComplete() {
		
		return overFlag;
	}
	String latestPage;
	public String getLatestPage() {
		return latestPage;
	}
	public void setLatestPage(String latestPage) {
		this.latestPage = latestPage;
	}
	@Override
	public long timeSpending() {
		// TODO Auto-generated method stub
		return proccessingTime;
	}
	
	
}

