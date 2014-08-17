package me.ralphya0.simple_html_fetcher.util.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

import me.ralphya0.simple_html_fetcher.util.ResourceProccessor;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.nodes.RemarkNode;
import org.htmlparser.tags.HeadTag;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;


public class ResourceProccessorImpl implements ResourceProccessor{

	public final static String C = System.getProperty("line.separator");
	static Map<String,String> srcMapping = new HashMap<String,String>();
	static int cssCount = 1;
	@Override
	public void pictureFilter(String siteDir) {
		
	}

	@Override
	public void styleFilter(Map<String,String> pageMapping,String fileBase,JTextArea ta) throws ParserException, IOException {
		String[] keySet = (String[])pageMapping.keySet().toArray(new String[0]);
		List<String> src = new ArrayList<String>();
		List<String> js = new ArrayList<String>();
		Parser p = new Parser(keySet[0]);
		NodeFilter f = new NodeClassFilter(HeadTag.class);
		NodeList ll = p.parse(f);
		String test = "";
		Node n = ll.elementAt(0);
		NodeList ls = n.getChildren();
		for(int j = 0;j < ls.size();j++){
			test = "";
			Node te = ls.elementAt(j);
			test = te.toHtml().trim();
			
			if(test != null && !test.equals("") && test.length() > 0){
				if(!(te instanceof RemarkNode)){
				
					
				
					if(test.indexOf("stylesheet")!=-1){
						//<link xxxx href="xxx" ...
						
						Tag tg = (Tag)te;
						String link = tg.getAttribute("href");
						//int a = test.indexOf("href=");
						//int b = test.indexOf(".css");
						//String link = test.substring(a+6, b+4);
							
						src.add(link);
					}
					
				}
			}
		}
		
		for(int i=0;i<src.size();i++){
			System.out.println("正在抓取资源文件："+src.get(i));
			ta.append("正在抓取资源文件："+src.get(i)+C);
			URL u = new URL(src.get(i));
			InputStream in = u.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String s = "";
			StringBuffer sb = new StringBuffer(); 
			while((s=br.readLine())!=null){
				sb.append(s+C);
			}
			String fileName = "res/css/style"+cssCount+".css";
			cssCount++;
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileBase + fileName));
			bw.write(sb.toString());
			br.close();
			bw.close();
			
			srcMapping.put(src.get(i), fileName);
			System.out.println("资源文件抓取成功："+src.get(i));
			ta.append("资源文件抓取成功："+src.get(i)+C);
		}
		
		System.out.println("++++++++++开始修改本地资源链接");
		ta.append("++++++开始修改本地资源链接..."+C);
		for(int i = 0;i < keySet.length;i++){
			String url = keySet[i];
			//System.out.println("开始处理页面："+fileBase+pageMapping.get(url));
			Parser ps = new Parser(fileBase+pageMapping.get(url));
			NodeList nl = ps.parse(null);
			StringBuffer sb = new StringBuffer();
			for(int j = 0;j < nl.size();j++){
				Node node = nl.elementAt(j);
				String tt = node.toHtml().trim();
				//++++++++++++++++应该注意嵌套标签的问题++++++++++++++
				if(tt != null && !tt.equals("") && tt.length() > 0){
					NodeList fhn = node.getChildren();
					if(fhn != null && fhn.size() > 0){
						for(int l = 0;l < fhn.size();l++){
							Node nc = fhn.elementAt(l);						
							if(nc instanceof HeadTag){
								//System.out.println("已定位到<head>标签！");
								HeadTag hd = (HeadTag)nc;
								NodeList cl = hd.getChildren();
								for(int k = 0;k < cl.size();k++){
									Node cd = cl.elementAt(k);
									String ts = cd.toHtml().trim();
									if(ts != null && !ts.equals("") && ts.length() > 0){
																	
										if(!(cd instanceof RemarkNode)){
																	
											if(ts.indexOf("stylesheet")!=-1){
												//System.out.println("已找到css标签！");
												Tag tg = (Tag)cd;
												//int a = ts.indexOf("href=");
												//int b = ts.indexOf(".css");
												
												//String link = ts.substring(a+6, b+4);
												String link = tg.getAttribute("href");
												//+++++++注意此处，有可能需要修改成   ../
												tg.setAttribute("href", "./"+srcMapping.get(link));
												//System.out.println("已成功处理css链接："+link+"++"+tg.getAttribute("href"));
												
											}
											else if(cd instanceof ScriptTag){
												//清除所有js链接
												ScriptTag st = (ScriptTag)cd;
												String jsSrc = st.getAttribute("src");
												if(jsSrc!=null&&!jsSrc.equals("")){
													st.removeAttribute("src");
												}
												else{
													st.setScriptCode(null);
												}
											}
										}
									}
								}
								break;
							}
						}
					}
					sb.append(node.toHtml()+C);
				}
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileBase+pageMapping.get(url)));
			bw.write(sb.toString());
			bw.close();
			
			
		}
		
	}

}
