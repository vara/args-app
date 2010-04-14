package vara.app.startupargs.Exception;

/**
 *
 * @author Grzegorz (vara) Warywoda
 */
public class ImmediatelyExitProgram extends Exception{
    public ImmediatelyExitProgram(){
        super();
    }
    public ImmediatelyExitProgram(String str){
        super(str);
    }
}
