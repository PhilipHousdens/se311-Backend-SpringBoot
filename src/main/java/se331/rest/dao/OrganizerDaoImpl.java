package se331.rest.dao;

import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import se331.rest.entity.Event;
import se331.rest.entity.Organizer;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrganizerDaoImpl implements OrganizerDao {
    List<Organizer> organizers;


    @PostConstruct
    public void init() {
        organizers = new ArrayList<>();

        organizers.add(Organizer.builder()
                .id(1L)
                .name("Kat Laydee")
                .address("Meow Town")
                .build());

        organizers.add(Organizer.builder()
                .id(2L)
                .name("Fern Pollin")
                .address("Flora City")
                .build());

        organizers.add(Organizer.builder()
                .id(3L)
                .name("Carey Wales")
                .address("Playa Del Carmen")
                .build());

        organizers.add(Organizer.builder()
                .id(4L)
                .name("Dawg Dahd")
                .address("Woof Town")
                .build());

        organizers.add(Organizer.builder()
                .id(5L)
                .name("Kahn Opiner")
                .address("Tin City")
                .build());

        organizers.add(Organizer.builder()
                .id(6L)
                .name("Brody Kill")
                .address("Highway 50")
                .build());
    }

    @Override
    public Integer getOrganizerSize() {return organizers.size();}

    @Override
    public Page<Organizer> getOrganizers(Integer pageSize, Integer page) {
        pageSize = pageSize == null ? organizers.size() : pageSize;
        page = page == null ? 1 : page;
        int firstIndex = (page - 1) * pageSize;
        return new PageImpl<Organizer>(organizers.subList(firstIndex, firstIndex + pageSize), PageRequest.of(page, pageSize), organizers.size());
    }

    @Override
    public Organizer getOrganizer(Long id) {
        return organizers.stream().filter(o -> o.getId().equals(id)).findFirst().orElse(null);
    }
}
