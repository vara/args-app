package vara.app.startupargs;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-18
 * Time: 05:04:04
 */
public interface GlobalParameter <T>{
	T getValue();

	/**
	 * Use this method  if can you know that this parameter has been set
	 *
	 * @return true if this parameter has been set
	 */
	boolean isSet();
}
