package utils;

public class MathUtils {
	private static final double EPSILON = 1E-4;

	public static double safedivision(double divident, double divisor, double defaultResult){
		return divisor<EPSILON?defaultResult:divident/divisor;
	} 

	public static boolean greater(double left, double right){
		return left - right > EPSILON;
	}
}
