package se331.rest.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se331.rest.dao.EventDao;
import se331.rest.dao.OrganizerDao;
import se331.rest.entity.Event;
import se331.rest.entity.Organizer;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizerServiceImpl implements OrganizerService {

    final OrganizerDao organizerDao;
    final EventDao eventDao;
    @Override
    public List<Organizer> getAllOrganizer() {
        return organizerDao.getOrganizer(Pageable.unpaged()).getContent();
    }

    @Override
    public Page<Organizer> getOrganizer(Integer page, Integer pageSize) {
        return organizerDao.getOrganizer(PageRequest.of(page, pageSize));
    };

    @Override
    @Transactional
    public Organizer save(Organizer organizer) {
        return organizerDao.save(organizer);
    }
}
