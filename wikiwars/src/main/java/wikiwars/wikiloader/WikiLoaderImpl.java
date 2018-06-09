package wikiwars.wikiloader;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class WikiLoaderImpl implements WikiLoader {
    @Override
    public Map<Instant, String> getArticleHistory(String title) {
        Map<Instant, String>  result = new HashMap<>();
        result.put(Instant.now(), "Tollerartikeltext");

        return result;
    }
}
