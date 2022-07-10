package com.example.link;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LINK_ID_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "LINK_ID_SEQUENCE_GENERATOR", sequenceName = "LINK_ID_SEQUENCE", allocationSize = 1)
    @Column(name = "LINK_ID")
    private Long id;

    @Column(name = "LINK_URL")
    private String url;

    @Column(name = "LINK_TITLE")
    private String title;

    @Column(name = "LINK_DESCRIPTION")
    private String description;
}
