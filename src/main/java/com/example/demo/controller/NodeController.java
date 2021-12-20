package com.example.demo.controller;

import com.example.demo.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author han.xue
 * @since 2021-08-09 17:51:51
 */
@RestController
@RequestMapping("/node")
public class NodeController {

    @Autowired
    private NodeService nodeService;

    /**
     * 查找两个点之间的所有最短路径(最短路径可能存在多个)
     * @param startNodeName 起始点
     * @param endNodeName 终点
     * @return
     */
    @RequestMapping("/findAllShortestPathByName/{startNodeName}/{endNodeName}")
    public String findAllShortestPathByName(@PathVariable("startNodeName") String startNodeName,
                                     @PathVariable("endNodeName") String endNodeName) {

        nodeService.findAllShortestPathByName(startNodeName, endNodeName);

        return "success";
    }

    /**
     * 查找两个点之间的所有路径
     * @param startNodeName 起始点
     * @param endNodeName 终点
     * @return
     */
    @RequestMapping("/findAllPathByName/{startNodeName}/{endNodeName}")
    String findAllPathByName(@PathVariable("startNodeName") String startNodeName,
                                     @PathVariable("endNodeName") String endNodeName) {

        nodeService.findAllPathByName(startNodeName, endNodeName);

        return "success";
    }


    /**
     * 带有事务的执行cypher语句
     * @param nodeNames 节点名称
     */
    @RequestMapping(value = "/actionWithTransaction", method = RequestMethod.POST)
    public String actionWithTransaction(@RequestBody List<String> nodeNames) {
        nodeService.actionWithTransaction(nodeNames);

        return "success";
    }

    /**
     * 没有事务的执行cypher语句
     * @param nodeNames 节点名称
     */
    @RequestMapping(value = "/actionWithoutTransaction", method = RequestMethod.POST)
    public String actionWithoutTransaction(@RequestBody List<String> nodeNames) {
        nodeService.actionWithoutTransaction(nodeNames);

        return "success";
    }
}
