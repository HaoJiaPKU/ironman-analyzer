package pattern;

import config.FilePath;
import utils.DictUtil;
import utils.XSSFInput;

public class TroubleShooting {
	
	public static void extractWord() {
		DictUtil.loadDict(DictUtil.problemWord, FilePath.ProblemPatternFile);
		DictUtil.loadDict(DictUtil.productWord, FilePath.ProblemProductFile);
		XSSFInput xi = new XSSFInput(FilePath.ProblemDataFile);
		String line[];
		while((line = xi.getNextRow()) != null) {
			for (int i = 0; i < line.length; i ++) {
				if (line[i] == null || (i != 2 && i != 3)) {
					continue;
				}
				System.out.print("problems : ");
				int indexStart = 0;
				while (indexStart != -1) {
					int tempIndex = 0x7fffffff;
					String subStr = new String();
					for (String str : DictUtil.problemWord) {
						int t = line[i].indexOf(str, indexStart);
						if (t != -1 && t < tempIndex) {
							tempIndex = t;
							subStr = str;
						}
					}
					if (tempIndex == 0x7fffffff) {
						break;
					} else {
						System.out.print(subStr + "	");
						indexStart = tempIndex + subStr.length();
					}
				}
				System.out.println();
				
				System.out.print("product : ");
				indexStart = 0;
				while (indexStart != -1) {
					int tempIndex = 0x7fffffff;
					String subStr = new String();
					for (String str : DictUtil.productWord) {
						int t = line[i].indexOf(str, indexStart);
						if (t != -1 && t < tempIndex) {
							tempIndex = t;
							subStr = str;
						}
					}
					if (tempIndex == 0x7fffffff) {
						break;
					} else {
						System.out.print(subStr + "	");
						indexStart = tempIndex + subStr.length();
					}
				}
				System.out.println();
			}
			System.out.println();
		}
	}
	
	public static void main(String args[]) {
		extractWord();
	}
}
