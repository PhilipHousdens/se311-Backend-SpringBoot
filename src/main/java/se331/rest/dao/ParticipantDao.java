package se331.rest.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.rest.entity.Participant;

import java.util.Optional;

public interface ParticipantDao {
    Page<Participant> getParticipant(Pageable pageRequest);
    Optional<Participant> findById(Long id);

}
