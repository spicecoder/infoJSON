/*
 *This program recreates the JSONFile that was broken down into 
 * context paths by the program InfoJSON
 * context paths can uniquely specify every 
 * element in a JSONObject
 * the convention followed is JSON{"name":"value"}
 * is represented by name.@value, i.e. each value is 
 * preceded by "@" in the context path
 * nesting is done by dots {"name1":{name2:"value2"}}
 * will be presented as name1.name2.@value
 * json array in a json is represented by numbers
 * thus {"myjson" :["run","play","behappy"]}
 * is represented by the three context paths:
 * myjson.0.@run, myjson.1.@play ,
 * myjson.2.@behappy
 * 
 * 
 * The overall benifit is the whole json file  elements
 * can be conveniently represented by a collection of 
 * urls.
 * 
 *Author :Pronab Pal
 */
package infojson;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import org.json.simple.JSONArray;
/**
 *
 * @author pronabpal
 */
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
public class InfoJSON {
  public JSONArray infoJSON = new JSONArray();

    public InfoJSON( ) {

        int ctr; JSONObject  injson = null;
//        try {
//            ctr = inputStream.read();
//            while (ctr != -1) {
//                narrativeStream.write(ctr);
//                ctr = inputStream.read();
//            }
//            inputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
       // Log.v("Text Data", narrativeStream.toString());
        try {
            // Parse the data into jsonobject to get original data in form of json.
           
            JSONParser parser = new JSONParser();
          Object obj = parser.parse(new FileReader("twitter.json"));

   injson  = (JSONObject) obj;
   String inj = injson.toJSONString();
   System.out.println("the input;" + inj);
    
     ContextObject  cj  = new ContextObject ();
     cj.jsonCaster(inj);
    
     cj.showContexts();
     cj.writeContexts();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
     // String in =   new FileReader("./output/narrative.json")
     InfoJSON inf = new InfoJSON();
     
    }
    
}
 


 