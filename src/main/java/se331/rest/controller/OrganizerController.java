package se331.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se331.rest.service.OrganizerService;
import se331.rest.util.LabMapper;

// Controller for class Organizer
@RestController
@RequiredArgsConstructor
public class OrganizerController {
    final OrganizerService organizerService;
    @GetMapping("/organizers")
    ResponseEntity<?> getOrganizers() {
        return ResponseEntity.ok(LabMapper.INSTANCE.getOrganizerDTO(organizerService.getAllOrganizer()));
    }
}
