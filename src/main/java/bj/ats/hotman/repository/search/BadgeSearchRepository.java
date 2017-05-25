package bj.ats.hotman.repository.search;

import bj.ats.hotman.domain.Badge;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Badge entity.
 */
public interface BadgeSearchRepository extends ElasticsearchRepository<Badge, Long> {
}
