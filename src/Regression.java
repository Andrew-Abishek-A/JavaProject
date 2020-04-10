import java.io.File;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMOreg;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

public class Regression {
	private static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + File.separator + "uploads";
	Instances dataset = null;
	SMOreg reg = null;
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
	public void loadModel(int column) {
		//set class index to last attribute --this is the column to be predicted
		dataset.setClassIndex(dataset.numAttributes()-1);
				
		//start building model
		reg = new SMOreg();
		try {
			reg.buildClassifier(dataset);
			//System.out.println(reg);
		} catch (Exception e) {
			System.out.println("Well fix this: loadModel: " +e);
		}
	}
	public String evaluate() {
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
	public String test(String Data) {
		Instance inst = dataset.firstInstance();
		String[] data = Data.split(",");
		for(int i=0;i<data.length;i++) {
			if(dataset.attribute(i).type() == Attribute.NUMERIC) {
				inst.setValue(dataset.attribute(i), Double.parseDouble(data[i]));
			}
			else if(dataset.attribute(i).type() == Attribute.NOMINAL || dataset.attribute(i).type() == Attribute.STRING)
				inst.setValue(dataset.attribute(i), data[i]);
			else
				inst.setValue(dataset.attribute(i), null);
		}
		//System.out.println(inst);
		//System.out.println(dataset.classAttribute().value(1));
		inst.setValue(dataset.attribute(data.length), 0);
		try {
			double attr = reg.classifyInstance(inst);
			return String.valueOf(attr);
		}
		catch(Exception e) {
		}
		return null;
	}
}
