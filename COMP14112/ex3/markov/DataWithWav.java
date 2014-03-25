package markov;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;

/** 
 * This class extends Data to also store sound wave amplitudes derived from 
 * the associated WAV files from another directory, and it has a method to return them.
 *
 */

public class DataWithWav extends Data {

    /**
     * Frame rate in WAV file in samples per second
     */

    static public int wavRate = 8000; 
    
    private ArrayList <short[]> ampList; // List containing sound amplitudes for each sample
    
    /** 
     * Constructor when the MFCC files and associated Wav files are provided
     */

    public DataWithWav (String pathMfcc, String pathWav) {
	super(pathMfcc);
	ampList = new ArrayList<short[]>();
        File fl = new File(pathWav);
	File[] fileArray = fl.listFiles(); // Array of file objects for all the files in the directory specified by pathWav

	// Loop through all examples in the directory specified by pathWav
	
	for(int i=0;i<fileArray.length;i++){
	    ArrayList <short[]> timeSequence = new ArrayList<short[]>(); // List of MFCC vectors for all times for one example
	    try {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileArray[i]);
		int bytesPerFrame = audioInputStream.getFormat().getFrameSize();
		int totalBytes = bytesPerFrame*(int) audioInputStream.getFrameLength();
		byte[] audioBytes = new byte[totalBytes];
		audioInputStream.read(audioBytes); 

		// Wav file stores sound wave amplitudes as short integers
		// so we need to convert the byte stream into an array of shorts

		short[] audioShorts = new short[totalBytes/2];
		for(int j=0;j<totalBytes/2;j++){
		    byte b = audioBytes[2*j];
		    byte a = audioBytes[2*j+1];
		    audioShorts[j] = (short)((a << 8) | (b & 0xff)); // Casting a byte to a short (non-standard so can't use Byte.toShort method)
		}
		ampList.add(audioShorts);
	    }
	    catch (UnsupportedAudioFileException uF) {
		uF.printStackTrace();
	    }
	    catch(IOException e){
		System.out.println(e);
	    }
	}
    }

    /**
     * Method to return an array of doubles containing one sound wave scaled to lie between -1 and 1.
     * The index exampleNumber starts at zero for the first example
     */

    public double[] getSoundWave (int exampleNumber){
	double[] scaledAmp = new double[ampList.get(exampleNumber).length];
	for(int i=0;i<ampList.get(exampleNumber).length;i++)
	    scaledAmp[i] = (double) ampList.get(exampleNumber)[i]/(double) 32768; // Normalise by largest possible short
	return scaledAmp;
    }
}



    
