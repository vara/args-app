package vara.app.startupargs.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vara.app.startupargs.ArgsParser;
import vara.app.startupargs.GBooleanValueParameter;
import vara.app.startupargs.GStringValueParameter;
import vara.app.startupargs.GlobalParameters;
import vara.app.startupargs.base.AbstractParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-18
 * Time: 04:55:53
 */
public class sampleForGlobalArgs extends Sample{
	private static final Logger log = LoggerFactory.getLogger(sampleForGlobalArgs.class);

	 @Override
	 protected List<AbstractParameter> createParameters(){
		log.info("Create parameters");

		List<AbstractParameter> params = new ArrayList();

		params.add(new GStringValueParameter("name","n") {
			@Override
			public String getOptionDescription() {
				return "Set name";
			}
		});

		params.add(new GBooleanValueParameter("boolean","b"){
			@Override
			public String getOptionDescription() {
				return "Set flag like a boolean value (true or false)";
			}
		});

		return params;
	}

	public static void main(String[] args) {
		//for init
		new sampleForGlobalArgs();

		try{
			ArgsParser.parseParameters(args);

		}catch (Exception e){ //for this catch see ArgsParser.setExceptionBehaviour
			log.warn("",e);
		}

		String sName = GlobalParameters.getStringValue("--name");
		String sName2 = GlobalParameters.getStringValue("n");


		log.info("Name1 : {} <==> Name2 : {} (should be te same !)",sName,sName2);
	}
}
