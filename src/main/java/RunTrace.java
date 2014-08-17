import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import me.ralphya0.simple_html_fetcher.processor.TracerProcessor;


public class RunTrace extends JFrame {
	public JPanel jp ;
	public JLabel lb1;
	public JLabel lb2;
	public JLabel lb3;
	public JLabel lb4;
	public JLabel lb10;
	public JLabel lb5;
	public JLabel lb6;
	public JLabel lb7;
	public JLabel lb8;
	public JTextField jf1;
	public JTextField jf2;
	public JButton bt;
	public JTextArea ta;
	public JMenuBar jmb;
	public JMenu jm;
	public JMenuItem it;
	public JButton bt2;
	public JButton bt3;
	public JScrollPane sp;
	public final static String C = System.getProperty("line.separator");
	public RunTrace(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 550);
		this.setResizable(false);
		this.setTitle("Khaos Tracer");
		
		jp = new JPanel();
		lb1 = new JLabel();
		lb2 = new JLabel();
		lb3 = new JLabel();
		lb4 = new JLabel();
		lb10 = new JLabel();
		lb5 = new JLabel();
		lb6 = new JLabel();
		lb7 = new JLabel();
		lb8 = new JLabel();
		jf1 = new JTextField();
		jf2 = new JTextField();
		bt = new JButton();
		bt2 = new JButton();
		bt3 = new JButton();
		ta = new JTextArea();
		jmb = new JMenuBar();
		jm = new JMenu();
		it = new JMenuItem();
		//-----
		it.setText("退出");
		jm.setText("菜单");	
		jm.add(it);
		jmb.add(jm);
		//------
		lb1.setText("操作提示：");
		lb2.setText("1. 在  站点地址   输入要抓取网站的链接 以'http://' 开头，如http://api.jquery.com");
		
		lb3.setText("2. 在  保存路径   输入本地存储路径");
		lb4.setText("3. 点击  开始抓取,并等待程序执行结束");
		lb10.setText("4. 在所选择的  保存路径  下查看被抓取下来的页面");
		lb1.setBounds(15, 165, 100, 20);
		lb2.setBounds(42, 195, 450, 20);
		lb3.setBounds(42, 215, 250, 20);
		lb4.setBounds(42, 235, 250, 20);
		lb10.setBounds(42, 255, 450, 20);
		lb5.setText("站点地址   :");
		lb6.setText("保存路径   :");
		lb5.setBounds(95, 58, 70, 20);
		lb6.setBounds(95, 95, 70, 20);
		lb7.setText("Khaos 1.0");
		lb7.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
		lb8.setText("by @ralphya0");
		lb8.setFont(new Font("Segoe Print",Font.PLAIN,17));
		lb7.setBounds(172, 15, 105, 23);
		lb8.setBounds(292, 15, 140, 23);
		jf1.setBounds(175, 58, 200, 20);
		jf2.setBounds(175, 95, 200, 20);
		jf2.setEditable(false);
		bt.setText("开始抓取");
		bt.setBounds(284, 135, 90, 20);
		bt.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//arguments verification
				String namespace = jf1.getText();
				String fileBase = jf2.getText();
				if(namespace.trim()!=null&&!namespace.trim().equals("")){
					if(fileBase!=null&&!fileBase.equals("")){
						//disable items in panel
						bt.setVisible(false);
						bt.setEnabled(false);
						bt2.setEnabled(false);
						bt2.setVisible(false);
						jf1.setEditable(false);
						ta.append("++++++根据目标站点规模,程序执行时间可能较长,请耐心等待！"+C);
						ta.append("++++++程序正在初始化..."+C);
						
						TracerProcessor tp = new TracerProcessor(namespace.trim(),fileBase,ta);
						ta.append("++++++初始化成功，开始抓取   "+namespace.trim()+" 数据..."+C);						
						new Thread(tp).start();
				
					}
					else{
						JOptionPane.showMessageDialog(null, "请选择存储路径！", "提示信息", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					jf1.setText("");
					JOptionPane.showMessageDialog(null, "请输入非空链接！", "提示信息", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		
		
		bt2.setText("选择路径");
		bt2.setBounds(385, 95, 95, 20);
		bt2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				jf2.setEditable(true);
				JFileChooser ch = new JFileChooser();
				ch.setDialogTitle("选择路径");
				ch.setApproveButtonText("确定");
				ch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int r = 0;
				r = ch.showOpenDialog(null);
				if(r==JFileChooser.APPROVE_OPTION){
					String ss = ch.getSelectedFile().getPath()+"\\";
					System.out.println(ss);
					jf2.setText(ss);
					jf2.setEditable(false);
				}
			}
			
		});
		
		bt3.setText("重置表格");
		bt3.setBounds(175, 135, 90, 20);
		bt3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jf1.setText("");
				jf2.setText("");
			}
			
		});
		
		//ta.setBounds(15,280, 530, 200);
		ta.setLineWrap(true);
		ta.setAutoscrolls(true);
		sp = new JScrollPane(ta);
		sp.setBounds(15, 280, 550, 200);
		//++++++++++
		jp.add(lb1);
		jp.add(lb2);
		jp.add(lb3);
		jp.add(lb4);
		jp.add(lb10);
		jp.add(lb5);
		jp.add(lb6);
		jp.add(lb7);
		jp.add(lb8);
		jp.add(jf1);
		jp.add(jf2);
		jp.add(bt);
		jp.add(bt2);
		jp.add(bt3);
		jp.add(sp);
		jp.setLayout(null);
		jp.setBackground(Color.WHITE);
		this.setJMenuBar(jmb);
		this.add(jp);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new RunTrace();
	}

}
