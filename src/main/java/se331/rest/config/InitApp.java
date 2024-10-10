package se331.rest.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se331.rest.entity.Event;
import se331.rest.entity.EventParticipantDTO;
import se331.rest.entity.Organizer;
import se331.rest.entity.Participant;
import se331.rest.repository.EventRepository;
import se331.rest.repository.OrganizerRepository;
import se331.rest.repository.ParticipantRepository;
import se331.rest.security.user.Role;
import se331.rest.security.user.User;
import se331.rest.security.user.UserRepository;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {
    final EventRepository eventRepository;
    final OrganizerRepository organizerRepository;
    final ParticipantRepository participantRepository;
    final UserRepository userRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        // Creating organizers
        Organizer org1 = organizerRepository.save(Organizer.builder().name("CAMT").build());
        Organizer org2 = organizerRepository.save(Organizer.builder().name("CMU").build());
        Organizer org3 = organizerRepository.save(Organizer.builder().name("ChiangMai").build());

        // Creating participants
        Participant parti1 = participantRepository.save(Participant.builder().name("John Doe").telNo("123-456-7890").build());
        Participant parti2 = participantRepository.save(Participant.builder().name("Jane Smith").telNo("234-567-8901").build());
        Participant parti3 = participantRepository.save(Participant.builder().name("Alice Johnson").telNo("345-678-9012").build());
        Participant parti4 = participantRepository.save(Participant.builder().name("Bob Brown").telNo("456-789-0123").build());
        Participant parti5 = participantRepository.save(Participant.builder().name("Carol White").telNo("567-890-1234").build());

        // Creating and initializing events with participants
        createEvent(org1, "Academic", "Midterm Exam", "A time for taking the exam", "CAMT Building", "3rd Sept", "3.00-4.00 pm", false, Arrays.asList(parti1, parti2, parti3));
        createEvent(org1, "Academic", "Commencement Day", "A time for celebration", "CMU Convention hall", "21th Jan", "8.00am-4.00pm.", false, Arrays.asList(parti1, parti2, parti3, parti4)); // 4 participants
        createEvent(org2, "Cultural", "Loy Krathong", "A time for Krathong", "Ping River", "21th Nov", "8.00am-10.00pm.", false, Arrays.asList(parti2, parti3, parti4)); // 3 participants
        createEvent(org3, "Cultural", "Songkran", "Let's Play Water", "Chiang Mai Moat", "13th April", "10.00am-6.00pm.", true, Arrays.asList(parti1, parti3, parti5)); // 3 participants
        addUser();
    }

    private void createEvent(Organizer organizer, String category, String title, String description, String location, String date, String time, boolean petAllowed, Iterable<Participant> participants) {
        Event event = Event.builder()
                .category(category)
                .title(title)
                .description(description)
                .location(location)
                .date(date)
                .time(time)
                .petAllowed(petAllowed)
                .build();

        event.setOrganizer(organizer);
        organizer.getOwnEvents().add(event);

        // Create EventParticipant instances and link them to participants and the event
        for (Participant participant : participants) {
            EventParticipantDTO eventParticipant = EventParticipantDTO.builder()
                    .event(event)
                    .participant(participant)
                    .participantName(participant.getName())
                    .eventTitle(event.getTitle())
                    .build();

            event.getParticipants().add(eventParticipant);
            participant.getEventHistories().add(eventParticipant);
        }

        // Saving the event
        eventRepository.save(event);

    }

    User user1, user2, user3;
    private void addUser() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user1 = User.builder()
                .username("admin")
                .password(encoder.encode("admin"))
                .firstname("admin")
                .lastname("admin")
                .email("admin@admin.com")
                .enabled(true)
                .build();
        user2 = User.builder()
                .username("user")
                .password(encoder.encode("user"))
                .firstname("user")
                .lastname("user")
                .email("enabled@user.com")
                .enabled(true)
                .build();
        user3 = User.builder()
                .username("disableUser")
                .password(encoder.encode("disableUser"))
                .firstname("disableUser")
                .lastname("disableUser")
                .email("disableUser@user.com")
                .enabled(false)
                .build();

        user1.getRoles().add(Role.ROLE_USER);
        user1.getRoles().add(Role.ROLE_ADMIN);

        user2.getRoles().add(Role.ROLE_USER);
        user2.getRoles().add(Role.ROLE_USER);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }
}
