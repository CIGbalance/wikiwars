package wikiwars.wikiloader;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface WikiLoader {
    Map<Instant, String> getArticleHistory(List<Long> revids);
    List<Long> getArticleRevids(String title);
}
