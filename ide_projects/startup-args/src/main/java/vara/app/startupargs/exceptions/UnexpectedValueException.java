package vara.app.startupargs.exceptions;

import vara.app.startupargs.BooleanValueParameter;
import vara.app.startupargs.base.AbstractParameter;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-01
 * Time: 11:36:11
 */
public class UnexpectedValueException extends ValidationObjectException{
	public UnexpectedValueException(AbstractParameter parameter,String str) {
		super(parameter,str);
	}

	public UnexpectedValueException(AbstractParameter parameter, Throwable e) {
		super(parameter,e);
	}
}
