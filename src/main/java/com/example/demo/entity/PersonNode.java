package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.util.Map;

@NodeEntity(label = "Person")
@Data
@NoArgsConstructor
public class PersonNode {

    /**
     * @GraphId也不支持
     * 需要改成@Id + @GeneratedValue
     */
    @Id
    @GeneratedValue
    Long id;

    @Property(name = "name")
    private String name;

    Map<String, PersonNode> dynamicRelationships;
}
