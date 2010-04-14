package vara.app.startupargs;

/**
 *
 * @author Grzegorz (vara) Warywoda
 */
public abstract class IntegerValueParameter extends SingleValueParameter{

    public IntegerValueParameter(String symbol,String shortSymbol) {
        super(symbol,shortSymbol);
    }

    @Override
    public void handleOption(String optionValue) {
        try{
            handleOption(Integer.parseInt(optionValue));
        } catch(NumberFormatException e){
            throw new IllegalArgumentException(e);
        }
    }
    
    public abstract void handleOption(int optionValue);
}
