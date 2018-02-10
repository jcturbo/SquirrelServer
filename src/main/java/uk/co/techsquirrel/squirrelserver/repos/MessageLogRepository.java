package uk.co.techsquirrel.squirrelserver.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface MessageLogRepository extends CrudRepository<MessageLog, Long> {
    
}