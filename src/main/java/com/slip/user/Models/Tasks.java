package com.slip.user.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tasks {
    @Id
    @GeneratedValue
    private Long id;
    private String ref;
    @NotEmpty
    private String description;
    private String userRef;
    private Instant createdAt;
    private Instant updatedAt;
}
