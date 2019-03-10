package jf;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;
/**
 * 文件复制程序
 * 编码采用UTF-8
 */
public class jf1 extends JFrame {

	private JPanel contentPane;
	public JPanel panel;
	public JLabel label;
	public JTextField text_start;
	public JPanel panel_1;
	public JLabel label_1;
	public JTextField text_end;
	public JProgressBar progressBar;
	public JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					jf1 frame = new jf1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public jf1() {
		setTitle("文件复制");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 611, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(38, 28, 509, 43);
		contentPane.add(panel);
		panel.setLayout(null);
		
		label = new JLabel("原文件路径");
		label.setBounds(10, 0, 76, 43);
		panel.add(label);
		
		text_start = new JTextField();
		text_start.setBounds(115, 0, 380, 43);
		panel.add(text_start);
		text_start.setColumns(10);
		
		panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(38, 95, 509, 43);
		contentPane.add(panel_1);
		
		label_1 = new JLabel("目标文件路径");
		label_1.setBounds(10, 0, 90, 43);
		panel_1.add(label_1);
		
		text_end = new JTextField();
		text_end.setColumns(10);
		text_end.setBounds(114, 0, 381, 43);
		panel_1.add(text_end);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(40, 166, 496, 19);
		contentPane.add(progressBar);
		
		btnNewButton = new JButton("开始复制");
		btnNewButton.addActionListener(new BtnNewButtonActionListener());
		btnNewButton.setBounds(38, 201, 108, 40);
		contentPane.add(btnNewButton);
	}
	private class BtnNewButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String t1 = text_start.getText();
			String t2 = text_end.getText();
			if(t1!=null&&t1!=""&&t2!=null&&t2!=""){
				try {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Filecopy(t1,t2);
						}
					}).start();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
	
	/**
	 * 文件复制的方法
	 */
	public void Filecopy(String s1,String s2){
		progressBar.setValue(0);
		if(s1.equals("")||s1==null) {
			JOptionPane.showMessageDialog(null, "源文件不能为空");
			return;
		}
		if(s2.equals("")||s2==null) {
			JOptionPane.showMessageDialog(null, "目标文件不能为空");
			return;
		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(s1);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "源文件不存在");
			return;
		}
		int flen = 0;
		try {
			flen = fis.available();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "源文件获取失败");
			return;
		}
		//System.out.println(flen);
		int js = JOptionPane.showConfirmDialog(null, "文件大小为："+flen+" B是否复制?", "文件复制", JOptionPane.YES_NO_OPTION);
		//System.out.println(js);
		if(js==0) {
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(s2);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "目标文件输出位置有误");
				return;
			}
			DataInputStream dis = new DataInputStream(fis);
			DataOutputStream dos = new DataOutputStream(fos);
			
			byte [] buf = new byte[1024];
			
			int icu = 0;
			int progress = 0 ;
			
			try {
				while((icu = dis.read(buf, 0, 1024))!=-1){
					dos.write(buf, 0, icu);
					dos.flush();
					progress+=icu;
					progressBar.setValue((int) (((double)progress/flen)*100));
					progressBar.setStringPainted(true);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				dos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				dis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileInputStream fischeck = null;
			try {
				fischeck = new FileInputStream(s2);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "文件复制失败");
				return;
			}
			JOptionPane.showMessageDialog(null, "文件复制成功");
			try {
				fischeck.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
