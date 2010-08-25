package vara.app.startupargs;

import vara.app.startupargs.base.AbstractParameter;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-18
 * Time: 05:04:04
 */
public interface GlobalParameter <T> extends AbstractParameter{
	/**
	 * Get value specified by generic type.
	 *
	 * @return value described by generic type
	 */

	T getValue();

	/**
	 * Use this method  if can you know that this parameter has been set
	 *
	 * @return true if this parameter has been set
	 */
	boolean isSet();
}
