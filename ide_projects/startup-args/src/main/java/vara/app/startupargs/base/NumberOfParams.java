package vara.app.startupargs.base;

/**
 * Abstract object to allow for define the ranges of numbers.
 *
 * User: Grzegorz (vara) Warywoda
 * Date: 2010-06-21
 * Time: 08:27:50
 */
public abstract class NumberOfParams extends Number{

	public static final NumberOfParams ZERO = new NumberOfParams(0){
		@Override	public boolean canLess() {	return false;	}
		@Override	public boolean canMore() {	return false;	}
	};

	public static final NumberOfParams ONE = new NumberOfParams(1){
		@Override	public boolean canLess() {	return false;	}
		@Override	public boolean canMore() {	return false;	}
	};

	public static final NumberOfParams ZERO_OR_MORE = new NumberOfParams(0){
		@Override	public boolean canLess() {	return false;	}
		@Override	public boolean canMore() {	return true;	}
	};

	public static final NumberOfParams ONE_OR_MORE = new NumberOfParams(1){
		@Override	public boolean canLess() {	return false;	}
		@Override	public boolean canMore() {	return true;	}
	};

	public static final NumberOfParams ONE_OR_ZERO = new NumberOfParams(1){
		@Override	public boolean canLess() {	return true;	}
		@Override	public boolean canMore() {	return false;	}
	};


	public NumberOfParams(int value) {
		if(value <0) throw new IllegalArgumentException("Value must be equals or grater then 0");
		this.value = value;
	}

	private int value = 0;


	abstract public boolean canMore();

	abstract public boolean canLess();
	

	@Override
	public byte byteValue() {
		return (byte)value;
	}

	@Override
	public double doubleValue() {
		return (double)value;
	}

	@Override
	public float floatValue() {
		return (float)value;
	}

	@Override
	public long longValue() {
		return (long)value;
	}

	@Override
	public short shortValue() {
		return super.shortValue();
	}

	@Override
	public int intValue() {
		return value;
	}

	/**
	 * Returns a <code>String</code> object representing this
	 * <code>Integer</code>'s value. The value is converted to signed
	 * decimal representation and returned as a string, exactly as if
	 * the integer value were given as an argument to the {@link
	 * java.lang.Integer#toString(int)} method.
	 *
	 * @return  a string representation of the value of this object in
	 *          base&nbsp;10.
	 */
	public String toString() {
		return String.valueOf(value);
	}

	/**
	 * Get string representation for this object.
	 * Returned String can contains arithmetical char '<' or '>'
	 * depending on the type of the object.
	 *
	 * @return
	 */
	public String toString2() {
		StringBuilder sb = new StringBuilder(7);

		sb.append(value);

		if(canLess()){
			sb.append(" or <");
		}
		
		if(canMore()){
			sb.append(" or >");
		}
		return sb.toString();
	}

	/**
	 * Returns a hash code for this <code>Integer</code>.
	 *
	 * @return  a hash code value for this object, equal to the
	 *          primitive <code>int</code> value represented by this
	 *          <code>Integer</code> object.
	 */
	public int hashCode() {
		return value;
	}

	/**
	 * Compares this object to the specified object.  The result is
	 * <code>true</code> if and only if the argument is not
	 * <code>null</code> and is an <code>Integer</code> object that
	 * contains the same <code>int</code> value as this object.
	 *
	 * @param   obj   the object to compare with.
	 * @return  <code>true</code> if the objects are the same;
	 *          <code>false</code> otherwise.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof NumberOfParams) {
			return value == ((NumberOfParams)obj).intValue();
		}
		return false;
	}

	public boolean check(int valueToCompare){

		if(valueToCompare > value){
			return canMore();
		}else if(valueToCompare < value){
			return canLess();
		}

		return true;
	}

	public boolean check(NumberOfParams num){
		return check(num);
	}
}
