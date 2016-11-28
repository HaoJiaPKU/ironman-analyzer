package utils;

import java.io.IOException;
import java.util.HashSet;

import config.FilePath;

public class DictUtil {
	
	public static final String StopSigns = "[\\p{P}~$`^=|<>～｀＄＾＋＝｜＜＞￥× \\s|\t|\r|\n]+";
	public static HashSet<String> problemWord = new HashSet<String>();
	public static HashSet<String> productWord = new HashSet<String>();
	public static HashSet<String> entityWord = new HashSet<String>();
	public static HashSet<String> actionWord = new HashSet<String>();
	public static HashSet<String> descriptionWord = new HashSet<String>();
	
	public static void loadDict(HashSet<String> dict, String path)
	{	
		FileInput fi = new FileInput(path);
		String line = new String();
		try {
			while((line = fi.reader.readLine()) != null) {
				String temp[] = line.trim().split("	+");
				for (int i = 0; i < temp.length; i ++) {
					temp[i] = temp[i].trim();
					if (temp[i].length() > 0 && !dict.contains(temp[i])) {
						dict.add(temp[i]);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fi.closeInput();
	}
	
	public static boolean isDictWord(String str, HashSet<String> dict) {
		return dict.contains(str);
	}
	
	public static boolean containsDictWord(String str, HashSet<String> dict) {
		for (String word : dict) {
			if (str.contains(word) || word.contains(str)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String [] args) {
		loadDict(entityWord, FilePath.RequirmentDescriptionFile);
		for (String action : entityWord) {
			System.out.println(action);
		}
	}	
}