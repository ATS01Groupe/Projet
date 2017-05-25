package bj.ats.hotman.repository.search;

import bj.ats.hotman.domain.Rooms;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Rooms entity.
 */
public interface RoomsSearchRepository extends ElasticsearchRepository<Rooms, Long> {
}
