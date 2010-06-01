/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vara.app.startupargs.exceptions;

import vara.app.startupargs.base.AbstractParameter;

/**
 *
 * @author Grzegorz (vara) Warywoda
 */
public class ValidationObjectException extends Exception{

	public AbstractParameter getParameter() {
		return parameter;
	}

	private AbstractParameter parameter;

	public ValidationObjectException(AbstractParameter parameter){
		this.parameter = parameter;

	}
	public ValidationObjectException(AbstractParameter parameter,String str){
		super(str);
		this.parameter = parameter;
	}

	public ValidationObjectException(AbstractParameter parameter,Throwable cause) {
		super(cause);
		this.parameter = parameter;
	}

	public ValidationObjectException(AbstractParameter parameter,String message, Throwable cause) {
		super(message, cause);
		this.parameter = parameter;
	}
}