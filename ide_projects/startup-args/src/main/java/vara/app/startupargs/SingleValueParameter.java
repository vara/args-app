package vara.app.startupargs;

import vara.app.startupargs.base.DefaultParameter;

/**
 *
 * @author Grzegorz (vara) Warywoda
 */
public abstract class SingleValueParameter extends DefaultParameter {

    public SingleValueParameter(String symbol,String shortSymbol) {
        super(symbol,shortSymbol);
    }

    @Override
    public void safeOption(String[] optionValues) {
        handleOption(optionValues[0]);
    }

    @Override
    public int getOptionValuesLength() {
        return 1;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    public abstract void handleOption(String optionValue);
}
