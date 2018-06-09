package wikiwars.wikiloader;

import java.util.List;

public class Query {


		  
		  public List<Page> pages;
		  
		  public class Page {
		    List<Revision> revisions;
		  }

		  public class Revision {
		    public int revid;
		    public String user;
		    public String timestamp;
		  }
		  
		  public String toString()
		  {
			return "" + pages.toString();
		  }
		  
		
}
