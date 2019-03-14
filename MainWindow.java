import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame implements ActionListener{
	JButton b1,b2,b3;
	Record recorder;
	public MainWindow() {
		b1 = new JButton("録音開始"); b1.setFont(new Font("ＭＳ ゴシック",Font.PLAIN,22));
		b2 = new JButton("録音終了"); b2.setFont(new Font("ＭＳ ゴシック",Font.PLAIN,22));
		b3 = new JButton("再生");     b3.setFont(new Font("ＭＳ ゴシック",Font.PLAIN,22));
		JPanel p = new JPanel();
		WavePanel wave = new WavePanel();
		p.setLayout(new GridLayout(1,3));
		p.add(b1); p.add(b2); p.add(b3);
		this.add(p);
		this.setBackground(Color.black);
	    this.setTitle("Voice Recorder");
	    this.setSize(500,300);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setVisible(true);
	    b1.setEnabled(true); b2.setEnabled(false); b3.setEnabled(false);
	    b1.addActionListener(this); b2.addActionListener(this); b3.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==b1) {
			//録音開始
			try {
				recorder = new Record(new File("midi/RecordAudio.wav"));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			b1.setEnabled(false); b2.setEnabled(true); b3.setEnabled(false);
		}
		if(e.getSource()==b2) {
			//録音終了
			recorder.stopRecording();
			b1.setEnabled(true); b2.setEnabled(false); b3.setEnabled(true);
		}
		if(e.getSource()==b3){
			//再生
			recorder.play("midi/RecordAudio.wav");
			JPanel p2 = new WavePanel();
		    this.add(p2);
			b1.setEnabled(true); b2.setEnabled(false); b3.setEnabled(true);
		}
	}
	
	public static void main(String[] args) {
		new MainWindow();
	}

}
