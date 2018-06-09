package wikiwars.wikiloader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WikiLoaderImpl implements WikiLoader {
	
	public Map<String, String> getArticleHistory(List<Long> revids){
            Map<String,String> results = new HashMap<String, String>();
            for(long revid : revids){
                String idQuery = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts|revisions&explaintext=true&format=json&revids="+revid;
                
                StringBuilder sb = new StringBuilder();
                URLConnection urlConn = null;
                InputStreamReader in = null;
                JSONParser parser= new JSONParser();
                try {
                      URL url = new URL(idQuery);
                      urlConn = url.openConnection();
                      if (urlConn != null)
                      urlConn.setReadTimeout(60 * 1000);
                      if (urlConn != null && urlConn.getInputStream() != null) {
                          in = new InputStreamReader(urlConn.getInputStream(),
                          Charset.defaultCharset());
                          BufferedReader bufferedReader = new BufferedReader(in);
                          if (bufferedReader != null) {
                              int cp;
                              while ((cp = bufferedReader.read()) != -1) {
                                      sb.append((char) cp);
                              }
                              bufferedReader.close();
                          }
                      }
                      in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } 

                String json = sb.toString();
                
                try {
			Object obj=parser.parse(json);
			JSONObject jsonObject= (JSONObject)obj;
			
			JSONObject query = (JSONObject) jsonObject.get("query");
			JSONObject pages = (JSONObject) query.get("pages");
			String index="";
			Iterator it =pages.keySet().iterator();
			while (it.hasNext())
			{
				index=(String) it.next();
			}
			JSONObject page = (JSONObject) pages.get(index);
			
			String extract = (String) page.get("extract");
                        int cutoff = extract.indexOf("== References ==");
                        extract = extract.substring(0, cutoff);
                        
                        JSONArray revisions = (JSONArray) page.get("revisions");
			
			//JSONObject r1 = (JSONObject) revisions.get(0);
			//System.out.println(r1.get("revid"));
			Iterator it1=revisions.iterator();
                        String timestamp = null;
			while(it1.hasNext())
			{
				JSONObject r = (JSONObject) it1.next();
				//System.out.println(r.get("revid").getClass());
				timestamp = (String) r.get("timestamp");
			}
                        
                        results.put(timestamp, extract);
			
		} catch (Exception e) {
			System.out.println("du bist aber verarscht worden ");
			e.printStackTrace();
			// TODO: handle exception
		}
                
            }

		
        return results;
	}
	
    @Override
    public List<Long> getArticleRevids(String title) {
        
        title = title.replaceAll("\\u0020", "%20");
        
        String idQuery = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&rvlimit=500&titles="
          + title;
        
        
        StringBuilder sb = new StringBuilder();
	    URLConnection urlConn = null;
	    InputStreamReader in = null;
	    try {
	          URL url = new URL(idQuery);
	          urlConn = url.openConnection();
	          if (urlConn != null)
	          urlConn.setReadTimeout(60 * 1000);
	          if (urlConn != null && urlConn.getInputStream() != null) {
	              in = new InputStreamReader(urlConn.getInputStream(),
	              Charset.defaultCharset());
	              BufferedReader bufferedReader = new BufferedReader(in);
	              if (bufferedReader != null) {
	            	  int cp;
	            	  while ((cp = bufferedReader.read()) != -1) {
	            		  sb.append((char) cp);
	            	  }
	            	  bufferedReader.close();
	              }
	          }
	          in.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } 
	    
	    String json = sb.toString();
	    
	    ArrayList<Long> revids = new ArrayList<>();
	    
	    JSONParser parser= new JSONParser();
	    try {
			Object obj=parser.parse(json);
			JSONObject jsonObject= (JSONObject)obj;
			
			JSONObject query = (JSONObject) jsonObject.get("query");
			JSONObject pages = (JSONObject) query.get("pages");
			String index="";
			Iterator it =pages.keySet().iterator();
			while (it.hasNext())
			{
				index=(String) it.next();
			}
			JSONObject page = (JSONObject) pages.get(index);
			
			JSONArray revisions = (JSONArray) page.get("revisions");
			
			//JSONObject r1 = (JSONObject) revisions.get(0);
			//System.out.println(r1.get("revid"));
			Iterator it1=revisions.iterator();
			while(it1.hasNext())
			{
				JSONObject r = (JSONObject) it1.next();
				//System.out.println(r.get("revid").getClass());
				long s = (long) r.get("revid");
				revids.add(s);
			}
			
			for(long l : revids) System.out.println(l);
			
		} catch (Exception e) {
			System.out.println("du bist aber verarscht worden ");
			e.printStackTrace();
			// TODO: handle exception
		}
        return revids;
    }
  
  public static void main(String[] args) {
    WikiLoader l = new WikiLoaderImpl();
    List<Long> revids = l.getArticleRevids("North Korea");
    l.getArticleHistory(revids);
    
  }
   
}

