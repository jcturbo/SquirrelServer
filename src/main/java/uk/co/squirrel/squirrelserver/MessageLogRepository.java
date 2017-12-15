package uk.co.squirrel.squirrelserver;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface MessageLogRepository extends CrudRepository<MessageLog, Long> {
    
}