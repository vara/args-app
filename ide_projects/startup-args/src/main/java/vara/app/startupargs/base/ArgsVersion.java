package vara.app.startupargs.base;

import vara.app.v4j.DefaultVersion;

/**
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-11-02
 * Time: 00:13:50
 */
public class ArgsVersion extends DefaultVersion{
	public static final ArgsVersion ver = new ArgsVersion();

	ArgsVersion() {
		super("ArgsStartup");
	}

	public static String getStringFullVersion(){
		return ver.getFullVersion();
	}
}
