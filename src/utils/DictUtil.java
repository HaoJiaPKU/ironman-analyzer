package utils;

import java.io.IOException;
import java.util.HashSet;

import config.FilePath;

public class DictUtil {
	
	public static final String StopSigns = "[\\p{P}~$`^=|<>～｀＄＾＋＝｜＜＞￥× \\s|\t|\r|\n]+";
	public static HashSet<String> problemWord = new HashSet<String>();
	public static HashSet<String> productWord = new HashSet<String>();
	
	public static void loadProblemWord()
	{	
		FileInput fi = new FileInput(FilePath.ProblemPatternFile);
		String line = new String();
		try {
			while((line = fi.reader.readLine()) != null) {
				String temp[] = line.trim().split("	+");
				for (int i = 0; i < temp.length; i ++) {
					temp[i] = temp[i].trim();
					if (temp[i].length() > 0 && !problemWord.contains(temp[i])) {
						problemWord.add(temp[i]);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fi.closeInput();
	}
	
	public static void loadProductWord()
	{
		FileInput fi = new FileInput(FilePath.ProblemProductFile);
		String line = new String();
		try {
			while((line = fi.reader.readLine()) != null) {
				String temp[] = line.trim().split("	+");
				for (int i = 0; i < temp.length; i ++) {
					temp[i] = temp[i].trim();
					if (temp[i].length() > 0 && !productWord.contains(temp[i])) {
						productWord.add(temp[i]);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fi.closeInput();
	}
	
	public static boolean isProblemWord(String str) {
		return problemWord.contains(str);
	}
	
	public static boolean isCityName(String str) {
		return productWord.contains(str);
	}

	public static void main(String [] args) {}	
}