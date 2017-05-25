package bj.ats.hotman.repository.search;

import bj.ats.hotman.domain.Duration;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Duration entity.
 */
public interface DurationSearchRepository extends ElasticsearchRepository<Duration, Long> {
}
