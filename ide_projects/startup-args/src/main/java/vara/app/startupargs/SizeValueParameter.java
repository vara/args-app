package vara.app.startupargs;

import java.awt.*;
import java.util.StringTokenizer;

/**
 *
 * @author Grzegorz (vara) Warywoda
 */
public abstract class SizeValueParameter extends SingleValueParameter{

    public SizeValueParameter(String symbol,String shortSymbol){
        super(symbol,shortSymbol);
    }

    @Override
    public void handleOption(String optionValue) {
        Dimension dim = parseDimension(optionValue);
        if(dim == null){
            throw new IllegalArgumentException();
        }
        
        handleOption(dim);
    }

    public abstract void handleOption(Dimension dimensionValue);

    public Dimension parseDimension(String dimensionValue){

        Dimension dim = null;
        StringTokenizer st = new StringTokenizer(dimensionValue, ",");
        if(st.countTokens() == 2){
            String xStr = st.nextToken();
            String yStr = st.nextToken();

            int x,y;
            try {
                x = Integer.parseInt(xStr);
                y = Integer.parseInt(yStr);

                dim = new Dimension(x, y);
            }catch(NumberFormatException e){                
            }
        }
        return dim;
    }
}
