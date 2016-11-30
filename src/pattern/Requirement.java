package pattern;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import config.FilePath;
import utils.DictUtil;
import utils.FileInput;
import utils.FileOutput;
import utils.Parser;
import utils.SegmenterUtil;
import utils.XSSFInput;

public class Requirement {
	
	//提取的词是否包含dobj等关系中的后位词，或nn关系中的后位词
	public static boolean containsWord(String str, ArrayList<String> words) {
		for (int i = 0; i < words.size(); i ++) {
			if (str.contains(words.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	//keyword对象的去重判断
	public static boolean containsKeyword(HashSet<Keyword> hkw, Keyword kw) {
		for (Keyword item : hkw) {
			if(item.word.contains(kw.word)) {
				return true;
			}
		}
		return false;
	}
	
	public static ArrayList<Keyword> extractWord(HashSet<String> dict,
			ArrayList<String> words, String type, String input) {
		ArrayList<Keyword> kw = new ArrayList<Keyword>();
		System.out.print(type + " : ");
		int indexStart = 0;
		while (indexStart != -1) {
			int tempIndex = 0x7fffffff;
			String subStr = new String();
			for (String str : dict) {
				int t = input.indexOf(str, indexStart);
				if (t != -1 && t < tempIndex) {
					tempIndex = t;
					subStr = str;
				}
			}
			if (tempIndex == 0x7fffffff) {
				break;
			} else {
				if (subStr.length() > 1 && containsWord(subStr, words)) {
					System.out.print(subStr + "	");
					kw.add(new Keyword(subStr, tempIndex));
				}
				indexStart = tempIndex + subStr.length();
			}
		}
		System.out.println();
		
//		Collections.sort(kw, (o1, o2) -> {
//			return o1.word.length() >= o2.word.length() ? -1 : 1;
//		});
//		HashSet<Keyword> hkw = new HashSet<Keyword>();
//		for (int i = 0; i < kw.size(); i ++) {
//			if (!containsKeyword(hkw, kw.get(i))) {
//				hkw.add(new Keyword(kw.get(i)));
//			}
//		}
//		kw.clear();
//		for (Keyword item : hkw) {
//			kw.add(new Keyword(item));
//		}
		Collections.sort(kw, new Comparator<Keyword>(){  
            @Override  
            public int compare(Keyword o1, Keyword o2) {  
            	return o1.pos <= o2.pos ? -1 : 1;
            }
        });
//		for (int i = 0; i < kw.size(); i ++) {
//			System.out.println(kw.get(i).word + "	" + kw.get(i).pos);
//		}
//		System.out.println();
		return kw;
	}
	
	public static ArrayList<TripleRelation> extractPattern(
			ArrayList<Keyword> action,
			ArrayList<Keyword> entity,
			ArrayList<Keyword> description) {
		ArrayList<TripleRelation> ret = new ArrayList<TripleRelation>();
		for (int i = 0; i < action.size(); i ++) {
			if (action.get(i).isUsed) {
				continue;
			}
			for (int j = 0; j < entity.size(); j ++) {
				if (entity.get(j).isUsed) {
					continue;
				}
				int disae = Math.abs(entity.get(j).pos
						- (action.get(i).pos + action.get(i).word.length()));
				if (disae <= 4) {
					action.get(i).isUsed = true;
					entity.get(j).isUsed = true;
					for (int k = 0; k < description.size(); k ++) {
						if (description.get(k).isUsed) {
							continue;
						}
						int disad = Math.abs(description.get(k).pos
								- (action.get(i).pos + action.get(i).word.length()));
						int dised = Math.abs(description.get(k).pos
								- (entity.get(j).pos + entity.get(j).word.length()));
						if (dised <= 4 || disad <= 4) {
							description.get(k).isUsed = true;
							TripleRelation tr = new TripleRelation(
									action.get(i).word,
									entity.get(j).word,
									description.get(k).word);
							ret.add(tr);
						}
					}
					TripleRelation tr = new TripleRelation(
							action.get(i).word,
							entity.get(j).word,
							"");
					ret.add(tr);
				}
			}
		}
		for (int i = 0; i < action.size(); i ++) {
			if (action.get(i).isUsed) {
				continue;
			}
			for (int j = 0; j < description.size(); j ++) {
				if (description.get(j).isUsed) {
					continue;
				}
				int disae = Math.abs(description.get(j).pos
						- (action.get(i).pos + action.get(i).word.length()));
				if (disae <= 4) {
					action.get(i).isUsed = true;
					description.get(j).isUsed = true;
					for (int k = 0; k < entity.size(); k ++) {
						if (entity.get(k).isUsed) {
							continue;
						}
						int disad = Math.abs(entity.get(k).pos
								- (action.get(i).pos + action.get(i).word.length()));
						int dised = Math.abs(entity.get(k).pos
								- (description.get(j).pos + description.get(j).word.length()));
						if (dised <= 4 || disad <= 4) {
							entity.get(k).isUsed = true;
							TripleRelation tr = new TripleRelation(
									action.get(i).word,
									entity.get(k).word,
									description.get(j).word);
							ret.add(tr);
						}
					}
					TripleRelation tr = new TripleRelation(
							action.get(i).word,
							"",
							description.get(j).word);
					ret.add(tr);
				}
			}
		}
		for (int i = 0; i < entity.size(); i ++) {
			if (entity.get(i).isUsed) {
				continue;
			}
			for (int j = 0; j < description.size(); j ++) {
				if (description.get(j).isUsed) {
					continue;
				}
				int disae = Math.abs(description.get(j).pos
						- (entity.get(i).pos + entity.get(i).word.length()));
				if (disae <= 4) {
					entity.get(i).isUsed = true;
					description.get(j).isUsed = true;
					for (int k = 0; k < action.size(); k ++) {
						if (action.get(k).isUsed) {
							continue;
						}
						int disad = Math.abs(action.get(k).pos
								- (entity.get(i).pos + entity.get(i).word.length()));
						int dised = Math.abs(action.get(k).pos
								- (description.get(j).pos + description.get(j).word.length()));
						if (dised <= 4 || disad <= 4) {
							action.get(k).isUsed = true;
							TripleRelation tr = new TripleRelation(
									action.get(k).word,
									entity.get(i).word,
									description.get(j).word);
							ret.add(tr);
						}
					}
					TripleRelation tr = new TripleRelation(
							"",
							entity.get(i).word,
							description.get(j).word);
					ret.add(tr);
				}
			}
		}
		return ret;
	}
	
	public static void batch() {
		FileOutput fo = new FileOutput("result.txt");
		
		DictUtil.loadDict(DictUtil.actionWord, FilePath.RequirmentActionFile);
		DictUtil.loadDict(DictUtil.entityWord, FilePath.RequirmentEntityFile);
		DictUtil.loadDict(DictUtil.descriptionWord, FilePath.RequirmentDescriptionFile);
		XSSFInput xi = new XSSFInput(FilePath.RequirmentDataFile);
		String line[];
		SegmenterUtil.loadSegmenter();
		int counter = 1;
		while((line = xi.getNextRow()) != null) {
			if (counter ++ > 100) {
				break;
			}
			
			line[0] = line[0].replaceAll(SegmenterUtil.StopSigns, "").trim();
			String string = SegmenterUtil.segmenter.classifyToString(line[0]);
			System.out.println(string);
			List list = Parser.parse(string);
			ArrayList<String> dep = new ArrayList<String>();
			for (int i = 0; i < list.size(); i ++) {
				String t = list.get(i).toString();
//				if (t.contains("dobj") || t.contains("nsubj") || t.contains("dep")) {//直接宾语
					dep.add(t.substring(t.indexOf(",") + 2,
							t.indexOf(",") + t.substring(t.indexOf(",")).indexOf("-")));
//				}
				System.out.println(t);
			}
//			for (int i = 0; i < list.size(); i ++) {
//				String t = list.get(i).toString();
//				if (t.contains("nn") && containsWord(t, dep)) {//名词组合
//					dep.add(t.substring(t.indexOf(",") + 2,
//							t.indexOf(",") + t.substring(t.indexOf(",")).indexOf("-")));
//				}
//				System.out.println(t);
//			}
			for (int i = 0; i < dep.size(); i ++) {
				System.out.println(dep.get(i));
			}
			System.out.println();
			
			for (int i = 0; i < line.length; i ++) {
				if (i == 0) {
					ArrayList<Keyword> action = extractWord(DictUtil.actionWord, dep, "action", line[i]);
					ArrayList<Keyword> entity = extractWord(DictUtil.entityWord, dep, "entity", line[i]);
					ArrayList<Keyword> description = extractWord(DictUtil.descriptionWord, dep, "description", line[i]);
					ArrayList<TripleRelation> tr = extractPattern(action, entity, description);
					
					for (int j = 0; j < tr.size(); j ++) {
						System.out.println("{" + tr.get(j).entity + "	"
								+ tr.get(j).action + "	"
								+ tr.get(j).description + "}");
						try {
							fo.t3.write("(" + tr.get(j).entity + ";"
									+ tr.get(j).action + ";"
									+ tr.get(j).description + ")//");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					try {
						fo.t3.newLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (i == 1) {
					System.out.println(line[i]);
				}
			}
			System.out.println();
		}
		
		fo.closeOutput();
	}
	
	public static void removeDup() {
		FileOutput fo = new FileOutput("result-1.txt");
		FileInput fi = new FileInput("result.txt");
		String line = new String();
		try {
			while ((line = fi.reader.readLine()) != null) {
				System.out.println(line);
				String temp[] = line.split("//");
				HashSet<String> hash = new HashSet<String>();
				for (int i = 0; i < temp.length; i ++) {
					System.out.println(temp[i]);
					if (temp[i] != null && temp[i].length() > 0) {
						if (hash.contains(temp[i])) {
							continue;
						}
						boolean contain = false;
						for (String str : hash) {
							if (str.contains(temp[i])) {
								contain = true;
								break;
							}
						}
						if (contain) {
							continue;
						}
						String dupStr = null;
						for (String str : hash) {
							if (temp[i].contains(str)) {
								dupStr = str;
								break;
							}
						}
						if (dupStr != null) {
							hash.remove(dupStr);
						}
						hash.add(temp[i]);
					}
				}
				for (String str : hash) {
					System.out.print(str + "//");
					fo.t3.write(str + "//");
				}
				System.out.println();
				fo.t3.newLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fi.closeInput();
		fo.closeOutput();
	}
	
	public static void main(String args[]) {
		batch();
		removeDup();
	}
}

class Keyword {
	public String word;
	public int pos;
	public boolean isUsed;
	
	public Keyword(String word, int pos) {
		this.word = word;
		this.pos = pos;
		this.isUsed = false;
	}
	
	public Keyword(Keyword e) {
		this.word = e.word;
		this.pos = e.pos;
		this.isUsed = e.isUsed;
	}
}

class TripleRelation {
	public String action;
	public String entity;
	public String description;
	
	public TripleRelation(String action, String entity, String description) {
		this.action = action;
		this.entity = entity;
		this.description = description;
	}
}
