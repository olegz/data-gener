package oz;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Random;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.conf.YarnConfiguration;

public class DataGenerator {
	
	private static Random random = new Random();

	public static void main(String[] args) throws Exception {
		
		int wordsPerLine = 10;
		if (args.length > 0){
			wordsPerLine = Integer.parseInt(args[0]);
		}
		int uniqueValues = Integer.MAX_VALUE;
		if (args.length > 1){
			uniqueValues = Integer.parseInt(args[1]);
		}
		int lines = 100;
		if (args.length > 2){
			lines = Integer.parseInt(args[2]);
		}
		
		YarnConfiguration configuration = new YarnConfiguration();
		FileSystem fs = FileSystem.get(configuration);
		Path generatedFile = fs.makeQualified(new Path("foo"));
		System.out.println("Writing file: " + generatedFile);
		System.out.println("words per line: " + wordsPerLine);
		System.out.println("unique values: " + uniqueValues);
		System.out.println("lines: " + lines);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fs.create(generatedFile)));
		for (int i = 0; i < lines; i++) {
			StringBuffer buffer = new StringBuffer();
			for (int y = 0; y < wordsPerLine; y++) {
				String word = "word-" + random.nextInt(uniqueValues);
				buffer.append(word);
				buffer.append(" ");
			}
			buffer.append("\n");
//			String line = buffer.toString();
			writer.write(buffer.toString());
//			System.out.println("Written line: " + i);
		}
		
		writer.close();
		System.out.println("Written file: " + generatedFile + "; Size: " + fs.getFileStatus(generatedFile).getLen());
	}
}