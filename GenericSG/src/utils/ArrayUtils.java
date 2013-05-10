package utils;

public class ArrayUtils {
		
	public static String format(int[] a){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i=0;i<a.length;i++){
			sb.append(a[i]);
			if(i < a.length -1) sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static String format(int[][] a){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<a.length;i++){
			sb.append(format(a[i]));
			if(i < a.length -1) sb.append("\n");
		}
		return sb.toString();
	}
	public static String format(double[] a){
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i=0;i<a.length;i++){
			sb.append(a[i]);
			if(i < a.length -1) sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static String format(double[][] a){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<a.length;i++){
			sb.append(format(a[i]));
			if(i < a.length -1) sb.append("\n");
		}
		return sb.toString();
	}
}
