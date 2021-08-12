package com.example.demo.controller;

import com.example.demo.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    String findAllShortestPathByName(@PathVariable("startNodeName") String startNodeName,
                                     @PathVariable("endNodeName") String endNodeName) {

        nodeService.findAllShortestPathByName(startNodeName, endNodeName);

        return "success";
    }

    /**
     * 查找两个点之间的所有最短路径(最短路径可能存在多个)
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
}
