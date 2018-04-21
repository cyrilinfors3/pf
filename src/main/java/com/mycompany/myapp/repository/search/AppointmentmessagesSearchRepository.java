package com.mycompany.myapp.repository.search;

import com.mycompany.myapp.domain.Appointmentmessages;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Appointmentmessages entity.
 */
public interface AppointmentmessagesSearchRepository extends ElasticsearchRepository<Appointmentmessages, Long> {
}
