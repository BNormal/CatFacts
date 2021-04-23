package cats;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.CompoundControl;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import javax.sound.sampled.SourceDataLine;

public class SoundManager {

	private final int BUFFER_SIZE = 128000;

	/**
	 * @param filename
	 *            the name of the file that is going to be played
	 */

	public void playSound(String filename) {

		Thread thread = new Thread() {
			public void run() {
				/* try { URL url = this.getClass().getResource(filename); soundFile = new File(url.getFile());
				 * System.out.println(soundFile.getPath()); } catch (Exception e) { error = "Error 1: " + e.toString();
				 * Main.type(error); System.exit(1); } */
				AudioInputStream audioStream = null;
				try {
					audioStream = AudioSystem.getAudioInputStream(CatFacts.soundFiles.get(filename));
				} catch (Exception e) {
					System.exit(1);
				}
				AudioFormat audioFormat = audioStream.getFormat();

				DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
				SourceDataLine sourceLine = null;
				try {
					sourceLine = (SourceDataLine) AudioSystem.getLine(info);
					sourceLine.open(audioFormat);
				} catch (LineUnavailableException e) {
					System.exit(1);
				} catch (Exception e) {
					System.exit(1);
				}

				sourceLine.start();

				playingSound = true;

				int nBytesRead = 0;
				byte[] abData = new byte[BUFFER_SIZE];
				while (nBytesRead != -1) {
					try {
						nBytesRead = audioStream.read(abData, 0, abData.length);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (nBytesRead >= 0) {
						@SuppressWarnings("unused")
						int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
					}
				}

				playingSound = false;

				sourceLine.drain();
				sourceLine.close();
			}
		};
		thread.start();
	}
	
	public void loadSounds() {
		URL url = null;
		String[] soundList = {"Ding Sound Effect.wav", "FAIL SOUND EFFECT.wav", "Meow sound effect.wav"};
		for (int i = 0; i < soundList.length; i++) {
			url = this.getClass().getResource("audio/" + soundList[i]);
			if (url != null)
				CatFacts.soundFiles.put(soundList[i], url);
		}
	}
	
	public void toggleMicrophone() {
		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		for (int i = 0; i < mixerInfos.length; i++) {
            Mixer mixer = AudioSystem.getMixer(mixerInfos[i]);
            int maxLines = mixer.getMaxLines(Port.Info.MICROPHONE);//MICROPHONE
            Port lineIn = null;
            FloatControl volCtrl = null;
            if (maxLines > 0) {
                try {
                    lineIn = (Port) mixer.getLine(Port.Info.MICROPHONE);
                    lineIn.open();
                    CompoundControl cc = (CompoundControl) lineIn.getControls()[0];
                    Control[] controls = cc.getMemberControls();
                    //String name = AudioSystem.getMixerInfo()[i].getName().replace("Port Microphone ", "");
                    //System.out.println("" + name);
                    for (Control c : controls) {
                        if (c instanceof FloatControl) {
                            volCtrl = (FloatControl) c;
                            volCtrl.setValue((float) volCtrl.getValue() > 0.0 ? 0 : 1);
                        }
                    }
 
                } catch (Exception ex) {
                    continue;
                }
            }
        }
	}
	
	public boolean micMuted() {
		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		for (int i = 0; i < mixerInfos.length; i++) {
            Mixer mixer = AudioSystem.getMixer(mixerInfos[i]);
            int maxLines = mixer.getMaxLines(Port.Info.MICROPHONE);//MICROPHONE
            Port lineIn = null;
            FloatControl volCtrl = null;
            if (maxLines > 0) {
                try {
                    lineIn = (Port) mixer.getLine(Port.Info.MICROPHONE);
                    lineIn.open();
                    CompoundControl cc = (CompoundControl) lineIn.getControls()[0];
                    Control[] controls = cc.getMemberControls();
                    //String name = AudioSystem.getMixerInfo()[i].getName().replace("Port Microphone ", "");
                    //System.out.println("" + name);
                    for (Control c : controls) {
                        if (c instanceof FloatControl) {
                            volCtrl = (FloatControl) c;
                            boolean muted = volCtrl.getValue() > 0 ? false : true;
                            return muted;
                        }
                    }
 
                } catch (Exception ex) {
                    continue;
                }
            }
        }
		return true;
	}

	public boolean playingSound = false;
}