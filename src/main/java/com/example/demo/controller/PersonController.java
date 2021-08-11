package com.example.demo.controller;

import com.example.demo.entity.LoveRelation;
import com.example.demo.entity.PersonNode;
import com.example.demo.service.PersonService;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Relationship;
import org.neo4j.ogm.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

/**
 * @author han.xue
 * @since 2021-08-09 17:51:51
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    // A driver maintains a connection pool for each remote Neo4j server. Therefore
    // the most efficient way to make use of a Driver is to use the same instance
    // across the application.
    private static Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "root"));

    @Autowired
    private PersonService personService;

    @RequestMapping("/addPerson/{name}")
    public PersonNode addPerson(@PathVariable("name") String name) {
        PersonNode person = new PersonNode();
        person.setName(name);
        return personService.addPerson(person);
    }

    @RequestMapping("/loves/{id1}/{id2}")
    public LoveRelation loves(@PathVariable("id1") String id1, @PathVariable("id2") String id2) {
        PersonNode person1 = personService.findOnePerson(Long.parseLong(id1));
        PersonNode person2 = personService.findOnePerson(Long.parseLong(id2));
        LoveRelation love = new LoveRelation();
        love.setStartNode(person1);
        love.setEndNode(person2);
        return personService.loves(love);
    }


    @RequestMapping("/findAllById/{id}")
    List<LoveRelation> findAllById(@PathVariable("id") Long id) {
        return personService.findAllById(id);
    }

    @RequestMapping("/path")
    Result path() {
        Result result = personService.searchPath();
        System.out.println(result);
        return result;
    }

    @RequestMapping("/path2/{startNodeName}/{endNodeName}")
    String path2(@PathVariable("startNodeName") String startNodeName, @PathVariable("endNodeName") String endNodeName) {



        try (Session session = driver.session()) {

            String cypher = "MATCH (p1:Node {name:'%s'}),(p2:Node{name:'%s'}),p=allshortestpaths((p1)-[*]->(p2)) RETURN p";

            String cql = String.format(cypher, startNodeName, endNodeName);

            System.out.println(cql);

            //result包含了所有的path
            StatementResult result = session.run(cql);

            while (result.hasNext()) {
                Record record = result.next();
                List<Value> value = record.values();

                for (Value i : value) {

                    Path path = i.asPath();

                    //得到path中的节点
                    for (Node node : path.nodes()) {
                        System.out.printf("node: %s, id=%s", node.asMap(), node.id());
                        System.out.println("--------------------");
                    }

                    //处理路径中的关系
                    for (Relationship relationship : path.relationships()) {

                        long startNodeId = relationship.startNodeId();
                        long endNodeId = relationship.endNodeId();
                        String relType = relationship.type();

                        System.out.printf("关系类型：%s, startNodeId: %s, toNodeId: %s", relType, startNodeId, endNodeId);

                        System.out.println("关系属性如下：");

                        //得到关系属性的健
                        //这里处理关系属性
                        for (String relKey : relationship.keys()) {
                            String relValue = relationship.get(relKey).asObject().toString();
                            System.out.println(relKey + "-----" + relValue);
                        }

                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                    }
                }

                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            }

            return "success";
        }
    }
}
