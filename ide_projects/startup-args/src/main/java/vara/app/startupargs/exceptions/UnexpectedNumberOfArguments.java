package vara.app.startupargs.exceptions;

import vara.app.startupargs.base.AbstractParameter;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-01
 * Time: 11:49:22
 */
public class UnexpectedNumberOfArguments extends ValidationObjectException{
	public UnexpectedNumberOfArguments(AbstractParameter parameter) {
		super(parameter);
	}

	public UnexpectedNumberOfArguments(AbstractParameter parameter,Throwable cause) {
		super(parameter,cause);
	}

	public UnexpectedNumberOfArguments(AbstractParameter parameter,String message, Throwable cause) {
		super(parameter,message, cause);
	}

	public UnexpectedNumberOfArguments(AbstractParameter parameter,String str) {
		super(parameter,str);
	}
}
