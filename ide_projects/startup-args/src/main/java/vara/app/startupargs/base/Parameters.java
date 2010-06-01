package vara.app.startupargs.base;

import org.apache.log4j.Logger;
import vara.app.startupargs.defaultImpl.DefaultHelpParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Grzegorz (vara) Warywoda
 */
public class Parameters {

	private static final Logger log = Logger.getLogger(Parameters.class);

	private final static List<AbstractParameter> mapOfParameters = new ArrayList();

	static {
		mapOfParameters.add(new DefaultHelpParameter("--help","-h"));
	}
	
	public static boolean putParameter(AbstractParameter parameter){

		if(parameter!=null){
			if(!mapOfParameters.contains(parameter)){
				return mapOfParameters.add(parameter);
			}else{
				log.warn("Detected multiply parameter in container for '"+parameter+"'");
			}
		}
		return false;
	}

	public static void putParameter(List<AbstractParameter> vap){
		if(vap != null){
			for (AbstractParameter ap : vap) {
				putParameter(ap);
			}
		}
	}

	public static void removeAll(){
		mapOfParameters.removeAll(mapOfParameters);
	}

	public static AbstractParameter getParameter(String symbol){

		EntryParameter entry  = new EntryParameter(symbol);

		int index = mapOfParameters.lastIndexOf(entry);
		if(index != -1) return mapOfParameters.get(index);
		return null;
	}

	public static List<DefaultParameter> getAllParameters(){
		Vector<DefaultParameter> allParams = new Vector(mapOfParameters);
		return allParams;
	}

	public static int numberOfParameters(){
		return mapOfParameters.size();
	}

	public static boolean isEmpty(){
		return mapOfParameters.isEmpty();
	}

	private static class EntryParameter{

		private int hashCode = -1;

		private final String symbolName;


		public EntryParameter(String symbolName){

			if(symbolName == null) throw new NullPointerException("Input parameter must be non-null !");
			this.symbolName = symbolName;

			hashCode = generateHashCode();
		}

		public String getSymbolName() {
			return symbolName;
		}

		protected int generateHashCode(){
			int hashCode = 3;
			hashCode = 97 * hashCode + (this.symbolName != null ? this.symbolName.hashCode() : 0);
			return hashCode;
		}

		@Override
		public int hashCode() {
			return hashCode;
		}

		@Override
		public boolean equals(Object obj) {

			if(log.isDebugEnabled()) log.debug("Equals Method: "+this+" equals "+obj);

			if( !(obj instanceof DefaultParameter)) return false;
			if(this == obj) return true;

			return ((DefaultParameter)obj).getSymbol().equals(symbolName) ||
						((DefaultParameter)obj).getShortSymbol().equals(symbolName) ;
		}

		@Override
		public String toString() {
			return getClass().getSimpleName()+"'"+ getSymbolName()+"'";
		}
	}
}
