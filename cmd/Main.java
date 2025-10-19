package cmd;
//Voice Speaker Appender by ViveTheJoestar, for Kaosu Reido
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Main {
	public static void appendVoiceSpeaker(File dir) throws IOException {
		byte[] nullQuote = {-1, 0}; //-1 essentially disables the quote
		File voiceSpeaker = dir.toPath().resolve("021_voice_speaker.dat").toFile();
		RandomAccessFile dat = new RandomAccessFile(voiceSpeaker, "rw");
		System.out.println("Appending voice speaker of unpacked " + dir.getName() + "...");
		for (int pos=648; pos<1160; pos+=2) {
			dat.seek(pos);
			dat.write(nullQuote);
		}
		dat.close();
	}
	public static void main(String[] args) {
		try {
			double time = 0;
			File src = null;
			Scanner sc = new Scanner(System.in);
			while (src == null) {
				System.out.println("Enter a valid path to a folder containing unpacked character costumes:");
				String path = sc.nextLine();
				File tmp = new File(path);
				if (tmp.isDirectory()) {
					File[] dirs = tmp.listFiles();
					if (dirs != null && dirs.length != 0) {
						for (File d: dirs) {
							if (d.isDirectory()) {
								src = tmp;
								String name = d.getName();
								String[] nameArr = name.toLowerCase().split("_");
								boolean checkReg = nameArr[nameArr.length-1].matches("\\dp");
								boolean checkDmg = nameArr[nameArr.length-2].matches("\\dp") && nameArr[nameArr.length-1].equals("dmg");
								if (checkReg || checkDmg) {
									long start = System.currentTimeMillis();
									appendVoiceSpeaker(d);
									long end = System.currentTimeMillis();
									double timePerCostume = (end-start)/1000.0;
									time += timePerCostume;
								}
								else System.out.println("WARNING: "+name+"is not an unpacked character costume. Skipping...");
							}
						}
					}
				}
				else System.out.println("ERROR: Invalid folder path!");
			}
			sc.close();
			System.out.println("yay "+time+" seconds of my life passed and voice speakers went brr");
		} catch (Exception e) {
			System.out.println("hey vive you fucked up again, look:\n"+e.getClass().getSimpleName()+" - "+e.getMessage());
		}
	}
}