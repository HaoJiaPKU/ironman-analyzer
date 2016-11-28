package config;

public class FilePath {
	
	private static final String EncodingOutput = "UTF-8";
	
	private static final String DataDir = "data";
	
	private static final String ProblemDataDir = FilePath.DataDir + "/1118-PKU-ProblemData";
	public static final String ProblemDataFile =
			FilePath.ProblemDataDir + "/VT_Problem_pattern_PKU.xlsx";
	public static final String ProblemPatternFile =
			FilePath.ProblemDataDir + "/all_question_pattern.txt";
	public static final String ProblemProductFile =
			FilePath.ProblemDataDir + "/Product_list.txt";
	
	private static final String RequirmentDataDir = FilePath.DataDir + "/1118-PKU-RequestData";
	public static final String RequirmentDataFile =
			FilePath.RequirmentDataDir + "/Jian Request Paragraph-Pattern.xlsx";
	public static final String RequirmentActionFile =
			FilePath.RequirmentDataDir + "/Jian CustomerRequest-term-action.txt";
	public static final String RequirmentEntityFile =
			FilePath.RequirmentDataDir + "/Jian CustomerRequest-term-entity.txt";
	public static final String RequirmentDescriptionFile =
			FilePath.RequirmentDataDir + "/Jian CustomerRequest-term-status description.txt";
}
