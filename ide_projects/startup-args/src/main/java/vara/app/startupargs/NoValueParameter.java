package vara.app.startupargs;

import vara.app.startupargs.base.DefaultParameter;

/**
 *
 * @author Grzegorz (vara) Warywoda
 */
public abstract class NoValueParameter extends DefaultParameter {

    /* This class represents a parameter which performs
     * the action defined and the program ends.
     */
    public NoValueParameter(String symbol, String shortSymbol){
        super(symbol,shortSymbol);
    }

    @Override
    public void safeOption(String[] optionValues) {
        handleOption();
    }

    @Override
    public int getOptionValuesLength() {
        return 0;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    public abstract void handleOption();
}
