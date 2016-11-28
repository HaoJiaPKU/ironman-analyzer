package utils;

import java.util.List;

import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.trees.international.pennchinese.ChineseGrammaticalStructure;
import edu.stanford.nlp.trees.international.pennchinese.ChineseTreebankLanguagePack;

public class Parser {
	
	public static List parse(String str) {
		LexicalizedParser lp = LexicalizedParser.loadModel("source/xinhuaFactoredSegmenting.ser.gz");
		Tree t = lp.parse(str);
		ChineseTreebankLanguagePack tlp = new ChineseTreebankLanguagePack();
		GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();  
		ChineseGrammaticalStructure gs = new ChineseGrammaticalStructure(t); 
		List tdl = gs.typedDependenciesCCprocessed();
//		for (int i = 0; i < tdl.size(); i ++) {
//			System.out.println(tdl.get(i));
//		}
		return tdl;
	}
	
	public static void main(String args[]) {
	}
}
