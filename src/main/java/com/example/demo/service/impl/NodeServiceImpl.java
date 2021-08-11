package com.example.demo.service.impl;

import com.example.demo.service.NodeService;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author han.xue
 * @since 2021-08-11 16:48:48
 */
@Service
public class NodeServiceImpl implements NodeService {

    @Autowired
    private Driver driver;


    @Override
    public void findAllShortestPathByName(String startNodeName, String endNodeName) {
        try (Session session = driver.session()) {

            String cypher = "MATCH (p1:Node {name:'%s'}),(p2:Node{name:'%s'}),p=allshortestpaths((p1)-[*]->(p2)) RETURN p";

            String cql = String.format(cypher, startNodeName, endNodeName);

            System.out.println("将要执行的cql为:" + cql);

            //result包含了所有的path
            StatementResult result = session.run(cql);

            while (result.hasNext()) {
                Record record = result.next();
                List<Value> value = record.values();

                for (Value i : value) {

                    Path path = i.asPath();

                    //得到path中的所有节点
                    for (Node node : path.nodes()) {
                        System.out.printf("id=%s; node: %s,", node.id(), node.asMap());
                        System.out.println();
                    }

                    //处理路径中的关系
                    for (Relationship relationship : path.relationships()) {

                        long startNodeId = relationship.startNodeId();
                        long endNodeId = relationship.endNodeId();
                        String relType = relationship.type();

                        System.out.printf("关系类型：%s, startNodeId: %s, toNodeId: %s", relType, startNodeId, endNodeId);
                        System.out.println();

                        if (relationship.keys().iterator().hasNext()) {
                            System.out.println("关系属性如下：");

                            //得到关系属性的健
                            //这里处理关系属性
                            for (String relKey : relationship.keys()) {
                                String relValue = relationship.get(relKey).asObject().toString();
                                System.out.println(relKey + "-----" + relValue);
                            }
                        }

                    }
                    // 一个路径结束
                }

                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            }
        }
    }
}
