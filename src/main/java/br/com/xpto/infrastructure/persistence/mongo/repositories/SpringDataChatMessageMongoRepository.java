package br.com.xpto.infrastructure.persistence.mongo.repositories;

import br.com.xpto.infrastructure.persistence.mongo.entities.ChatMessageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataChatMessageMongoRepository extends MongoRepository<ChatMessageDocument, String> {
    List<ChatMessageDocument> findByRentalIdOrderByTimestampAsc(String rentalId);
}
