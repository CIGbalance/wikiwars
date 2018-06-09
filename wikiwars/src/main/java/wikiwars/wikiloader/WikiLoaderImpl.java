package wikiwars.wikiloader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WikiLoaderImpl implements WikiLoader {
    @Override
    public Map<Instant, String> getArticleHistory(String title) {
        Map<Instant, String>  result = new HashMap<>();
        result.put(Instant.now(), "Tollerartikeltext");
        
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
	    
	    Gson gson=new GsonBuilder().create();
	    Query r = gson.fromJson(json, Query.class);
	    System.out.println(r.toString());

        return result;
    }
  
  public static void main(String[] args) {
    WikiLoader l = new WikiLoaderImpl();
    l.getArticleHistory("North Korea");
    
  }
   
}

