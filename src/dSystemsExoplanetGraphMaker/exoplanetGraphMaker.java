package dSystemsExoplanetGraphMaker;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;

public class exoplanetGraphMaker {
	public static void print(Object x) {
		System.out.println(x);
	}
	
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Boolean commandQuit = false;
		print("Running....");
		
		String url = args[0];
		url.replaceAll("\"", " ");//Get url and remove " "
		try {
			String json = Jsoup.connect(url).ignoreContentType(true).execute().body();
			print(json);
			while(!commandQuit.equals(true)) {
				print("Enter command please");
				String command = br.readLine();
				print("Command: " + command);
				if(command.equals("command:quit")) {
					print("status:quitting"); 
					commandQuit = true;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
