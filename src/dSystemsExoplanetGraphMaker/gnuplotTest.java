package dSystemsExoplanetGraphMaker;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class gnuplotTest {
	public static void main(String[] args) {
		try {
			Runtime commandPromt = Runtime.getRuntime();
			Process proc = commandPromt.exec("\"C:\\Program Files\\gnuplot\\bin\\gnuplot.exe\" \"C:\\\\Users\\\\braft\\\\OneDrive\\\\Desktop\\\\test\\\\sin.plt\"");
			proc.waitFor();
		}catch (Exception e) {
		  System.err.print("didn't work");
		}
	}
}
