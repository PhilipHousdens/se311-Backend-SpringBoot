package se331.lab.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Organizer {
    private int id;
    private String name;
    private String address;
}
