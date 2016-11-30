package utils;

import java.util.Properties;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

public class SegmenterUtil {
	
	public static CRFClassifier<CoreLabel> segmenter;
	public static final String StopSigns = "[\\p{P}~$`^=|<>～｀＄＾＋＝｜＜＞￥× \\s|\t|\r|\n]+";

	public static void loadSegmenter()
	{	
		String basedir = "../stanford-segmenter-2015-12-09/data";
		Properties props = new Properties();
		props.setProperty("sighanCorporaDict", basedir);
		//props.setProperty("NormalizationTable", "data/norm.simp.utf8");
		//props.setProperty("normTableEncoding", "UTF-8");
		//below is needed because CTBSegDocumentIteratorFactory accesses it
		props.setProperty("serDictionary", basedir + "/dict-chris6.ser.gz");
		//props.setProperty("testFile", args[0]);
		props.setProperty("inputEncoding", "UTF-8");
		props.setProperty("sighanPostProcessing", "true");

		segmenter = new CRFClassifier<CoreLabel>(props);
		segmenter.loadClassifierNoExceptions(basedir + "/ctb.gz", props);
		//segmenter.classifyAndWriteAnswers(args[0]);
		//System.out.println(segmenter.classifyToString("今天天气不错啊"));
	}
	
	public static void main(String [] args) {}	
}