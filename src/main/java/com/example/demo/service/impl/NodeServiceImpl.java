package com.example.demo.service.impl;

import com.example.demo.service.NodeService;
import org.neo4j.driver.v1.*;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String cypher = "MATCH (p1:Node {name:'%s'}),(p2:Node{name:'%s'}),p=allshortestpaths((p1)-[*]->(p2)) RETURN p";

        String cql = String.format(cypher, startNodeName, endNodeName);

        findPath(cql);
    }

    private void findPath(String cql) {
        try (Session session = driver.session()) {

            System.out.println("将要执行的cql为:" + cql);

            //result包含了所有的path
            StatementResult result = session.run(cql);

            while (result.hasNext()) {
                Record record = result.next();
                List<Value> value = record.values();

                StringBuffer line = null;
                for (Value i : value) {

                    line = new StringBuffer();

                    Path path = i.asPath();

                    Map<Long, Node> nodeCache = new HashMap<>();

                    //得到path中的所有节点
                    for (Node node : path.nodes()) {
                        System.out.printf("id=%s; node: %s,", node.id(), node.asMap());
                        System.out.println();

                        nodeCache.put(node.id(), node);
                    }

                    //处理路径中的关系
                    for (Relationship relationship : path.relationships()) {

                        long startNodeId = relationship.startNodeId();
                        Node startNode = nodeCache.get(startNodeId);

                        long endNodeId = relationship.endNodeId();
                        Node endNode = nodeCache.get(endNodeId);

                        String relType = relationship.type();

                        System.out.printf("关系类型：%s, startNodeId: %s, startNodeName=%s, endNodeId: %s, endNodeName=%s"
                                , relType, startNodeId, startNode.asMap().get("name"), endNodeId,
                                endNode.asMap().get("name"));
                        System.out.println();

                        line.append(startNode.asMap().get("name")).append("-").append(endNode.asMap().get("name")).append("-");

                        if (relationship.keys().iterator().hasNext()) {
                            System.out.println("关系属性如下：");

                            //得到关系属性的健
                            //这里处理关系属性
                            for (String relKey : relationship.keys()) {
                                String relValue = relationship.get(relKey).asObject().toString();
                                System.out.println(relKey + "-----" + relValue);
                            }
                        }

                    }// 一个关系结束

                }// 一个路径结束

                System.out.println("+++++++++++++++++++++++++++++" + line.toString() + "++++++++++++++++++++++++++++++++++++");
            }
        }
    }

    @Override
    public void findAllPathByName(String startNodeName, String endNodeName) {
        String cypher = "MATCH (p1:Node {name:'%s'}),(p2:Node{name:'%s'}),p=(p1)-[*]->(p2) RETURN p";

        String cql = String.format(cypher, startNodeName, endNodeName);

        findPath(cql);
    }
}
