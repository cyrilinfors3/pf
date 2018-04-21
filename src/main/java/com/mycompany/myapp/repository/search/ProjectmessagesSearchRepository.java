package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Projectmessages;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Projectmessages entity.
 */
public interface ProjectmessagesSearchRepository extends ElasticsearchRepository<Projectmessages, Long> {
}
