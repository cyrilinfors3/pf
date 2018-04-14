package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Userextra;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Userextra entity.
 */
public interface UserextraSearchRepository extends ElasticsearchRepository<Userextra, Long> {
}
