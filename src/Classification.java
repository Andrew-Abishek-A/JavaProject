import java.io.File;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.core.Instances;
import weka.core.converters.CSVLoader;


public class Classification {
	
	private static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + File.separator + "uploads";
	Instances dataset = null;
	public String newDataSet(boolean header) {
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
			dataset = loader.getDataSet();
			System.out.println(dataset.toSummaryString());
			return dataset.toSummaryString();
		}
		catch(Exception e) {
			System.out.println("Well fix this: " +e);
			return null;
		}
	}
	public SMO loadModel(int column) {
		//set class index to last attribute --for some random reason, cheggit
		dataset.setClassIndex(dataset.numAttributes()-1);
				
		//start building model
		SMO smo = new SMO();
		try {
			smo.buildClassifier(dataset);
			return smo;
		} catch (Exception e) {
			System.out.println("well fix this: " +e);
			return null;
		}
		//System.out.println(smo);
	}
	public String evaluate(SMO smo) {
		Evaluation evaluation = null;
		try {
			evaluation = new Evaluation(dataset);
			evaluation.evaluateModel(smo, dataset);
			System.out.println(evaluation.toSummaryString());
			return evaluation.toSummaryString();
		} catch (Exception e) {
			System.out.println("Well fix this: " +e);
			return null;
		}
	}
}
