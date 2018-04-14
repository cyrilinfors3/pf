package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Projectevolution;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Projectevolution entity.
 */
public interface ProjectevolutionSearchRepository extends ElasticsearchRepository<Projectevolution, Long> {
}
