package me.ralphya0.simple_html_fetcher.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

import me.ralphya0.simple_html_fetcher.util.LinkParser;
import me.ralphya0.simple_html_fetcher.util.PageLoader;
import me.ralphya0.simple_html_fetcher.util.ResourceProccessor;
import me.ralphya0.simple_html_fetcher.util.impl.LinkParserImpl;
import me.ralphya0.simple_html_fetcher.util.impl.PageLoaderImpl;
import me.ralphya0.simple_html_fetcher.util.impl.ResourceProccessorImpl;

import org.htmlparser.util.ParserException;


public class TracerProcessor implements Runnable{
	public final static String C = System.getProperty("line.separator");
	private static PageLoader loader = new PageLoaderImpl();
	private static LinkParser parser = new LinkParserImpl();
	private static ResourceProccessor rp = new ResourceProccessorImpl();
	public String namespace;
	public String fileBase;
	public JTextArea ta;
	public TracerProcessor(String url,String fileBasePath,JTextArea t){
		this.namespace = url;
		this.fileBase = fileBasePath;
		this.ta = t;
	}
	
	public boolean traceSite(String url,String fileBasePath,JTextArea ta) throws ParserException, IOException{
		Date begin = new Date();
		final String fileBase = fileBasePath;
		final String namespace = url;
		boolean overFlag = false;
		List<String> urls = new ArrayList<String>();
		Map<String,String> pageMapping = null;
		
		//initialize
		urls.add(url);
		List<String> ls;
		ls = urls;
		while(!overFlag){
			pageMapping = loader.savePage(ls,fileBase,ta);
			overFlag = loader.tasksComplete();
			if(overFlag){
				break;
			}
			ls = null;
			ls = parser.parseLink(pageMapping, namespace,fileBase,ta);
			//pageMapping = null;
		}
		rp.styleFilter(pageMapping, fileBase,ta);
		Date over = new Date();
		ta.append("++++++PageLoader 执行总时间为："+loader.timeSpending()+" 秒"+C);
		ta.append("++++++LinkParser 执行总时间为："+parser.timeSpending()+" 秒"+C);
		ta.append("++++++Congratulations , 站点抓取完成！"+C);
		ta.append("++++++程序执行总时间为："+(over.getTime()-begin.getTime())/1000+" 秒");
		return true;
	}


	@Override
	public void run() {
		try {
			this.traceSite(namespace, fileBase,ta);
		} catch (ParserException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
