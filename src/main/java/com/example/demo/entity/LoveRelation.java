package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "LOVES")
@Data
@NoArgsConstructor
public class LoveRelation {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private PersonNode startNode;

    @EndNode
    private PersonNode endNode;
}
