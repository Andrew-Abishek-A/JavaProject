import java.io.File;
import weka.core.Instances;
import weka.core.converters.CSVLoader;


public class Classification {
	
	private static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + File.separator + "uploads";
	public void newDataSet(boolean header) {
		//load the dataset
		try {
			File file = new File(UPLOAD_DIRECTORY);
			String[] fileNames = file.list();
			int len = fileNames.length;
			CSVLoader loader = new CSVLoader();
			for(int i=0; i<len; i++) {
				loader.setSource(new File(UPLOAD_DIRECTORY + File.separator + fileNames[i]));
			}
			if(!header) {
				String[] options = new String[1]; 
				options[0] = "-H";
				loader.setOptions(options);
			}
			Instances dataset = loader.getDataSet();
			System.out.println(dataset.toSummaryString());
		}
		catch(Exception e) {
			
		}
		
	}
}
