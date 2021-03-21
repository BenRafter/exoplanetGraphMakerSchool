package dSystemsExoplanetGraphMaker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
			
			
			while(!commandQuit.equals(true)) {
				print("Enter command please");
				String command = br.readLine();
				if(command.equals("command:quit")) {
					print("status:quitting"); 
					commandQuit = true;
				}else if(command.equals("command:plot,xAxis:pl_orbper,yAxis:pl_orbeccen")) {
					print("Doing graph plot");
					String contents = Jsoup.connect(url).ignoreContentType(true).execute().body();
					JSONParser parser = new JSONParser();
					Object obj = parser.parse(contents);
					JSONArray jsonArray = (JSONArray) obj;
					for(int i = 0; i < jsonArray.size(); i++) {
						JSONObject currentJSON = (JSONObject) jsonArray.get(i);
						if(currentJSON.get("pl_orbper") != null && currentJSON.get("pl_orbeccen") != null) {
							print("Acceptable planet " + currentJSON);
						}else {
							print("Not an acceptable planet " + currentJSON);
						}
					}
					//String test = (String) jsonObj.get("pl_name");
				}else {
					print("Please enter a valid command");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
