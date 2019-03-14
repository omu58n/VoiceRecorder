import javax.sound.sampled.*;
import java.io.*;
public class Record implements Runnable {
	static final long RECORD_TIME = 100;  // 0.1 sec
	File wavFile;
	AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
	static final float SAMPLE_RATE = 44100;
	static final int SAMPLE_SIZE_IN_BITS = 16;
	static final int CHANNELS = 2;
	static final boolean SIGNED = true;
	static final boolean BIG_ENDIAN = true;
	TargetDataLine line;
	Record(File file) throws Exception {
		AudioFormat format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);
		wavFile = file;
		line = AudioSystem.getTargetDataLine(format);
		line.open(format);
		Thread stopper = new Thread(this);
		stopper.start();
		System.out.println("OK");
	}
	void startRecording() {
		try {
			line.start();
			System.out.println("Start");
			AudioInputStream ais = new AudioInputStream(line);
			AudioSystem.write(ais, fileType, wavFile);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	void stopRecording() {
		line.stop();
		line.close();
		System.out.println("stop");
	}
	void play(String file) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(file));
			byte [] data = new byte [ais.available()];
			ais.read(data);
			ais.close();
			AudioFormat format = ais.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			SourceDataLine line = (SourceDataLine)AudioSystem.getLine(info);
			line.open(format);
			line.start();
			line.write(data, 0, data.length);
			System.out.println("play");
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	public void run() {
	    startRecording();
	}
}
