import java.io.File;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMOreg;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class Regression {
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
	public SMOreg loadModel(int column) {
		//set class index to last attribute --for some random reason, cheggit
		dataset.setClassIndex(dataset.numAttributes()-1);
				
		//start building model
		SMOreg reg = new SMOreg();
		try {
			reg.buildClassifier(dataset);
			//System.out.println(reg);
			return reg;
		} catch (Exception e) {
			System.out.println("Well fix this: loadModel: " +e);
			return null;
		}
	}
	public String evaluate(SMOreg reg) {
		Evaluation evaluation = null;
		try {
			evaluation = new Evaluation(dataset);
			evaluation.evaluateModel(reg, dataset);
			System.out.println(evaluation.toSummaryString());
			return evaluation.toSummaryString();
		}
		catch(Exception e) {
			System.out.println("Well fix this: evaluate: " +e);
			return null;
		}
	}
}
