package com.example.demo.service;

/**
 * @author han.xue
 * @since 2021-08-11 16:46:46
 */
public interface NodeService {
    /**
     * 查找两个点之间最短路径(如果存在多个最短路径，也会查到)
     * @param startNodeName 始发节点
     * @param endNodeName 目的节点
     */
    void findAllShortestPathByName(String startNodeName, String endNodeName);
}
