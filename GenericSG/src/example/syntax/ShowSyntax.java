package example.syntax;

import example.syntax.minbasis.Basis;
import example.syntax.minbasis2.Basis2;
import example.syntax.saddlepoint.Saddle;

public class ShowSyntax {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(new Saddle().format(true));
		System.out.println("====");
		System.out.println(new Basis().format(true));
		System.out.println("====");
		System.out.println(new Basis2().format(true));
	}

}
