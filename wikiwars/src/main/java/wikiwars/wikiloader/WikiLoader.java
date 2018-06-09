package wikiwars.wikiloader;

import java.time.Instant;
import java.util.Map;

public interface WikiLoader {
    Map<Instant, String> getArticleHistory(String title);
}
