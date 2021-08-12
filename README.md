# neo4j-java-cypher-demo
## 简介
spring boot中使用neo4j的一些demo代码，依赖了spring-boot-starter-data-neo4j包,其他的依赖包可阅读pom.xml文件

## 如何使用
### 运行neo4j服务
推荐使用docker搭建neo4j服务
拉取镜像(jdk8与neo4j 3.x.x相兼容)
```bash
docker pull neo4j:3.5.22-community
```

启动一个容器
```bash
docker run --name neo4j -d \
    --publish=7474:7474 --publish=7687:7687 \
    --volume=/Users/johnxue/docker_data/neo4j/data:/var/lib/neo4j/import \
    neo4j:3.5.22-community
```

> 上面的挂载卷是可选的，如果不需要使用neo4j的import导入功能的话，可以不挂，记得修改成自己本机的路径，
> 如果不想链接neo4j时需要密码，可以加入--env=NEO4J_AUTH=none参数

启动完成后访问http://localhost:7474/browser/ 即可看到如下界面

![](https://data-repository-01.oss-cn-shanghai.aliyuncs.com/img/Screen%20Shot%202021-08-11%20at%2018.39.11.png)

### 插入示例数据

在Neo4J WebUI的控制台中,执行下方语句
```sql
// 创建节点
CREATE (a:Node{name: '北京'});
CREATE (a:Node{name: '上海'});
CREATE (a:Node{name: '苏州'});
CREATE (a:Node{name: '南京'});
CREATE (a:Node{name: '杭州'});
create (:Node{name:'徐州'}), (:Node{name:'淮安'}), (:Node{name:'宿迁'});

// 创建一个约束
CREATE CONSTRAINT ON (a:Node) ASSERT a.name IS UNIQUE;

// 创建关系
MATCH (a:Node{name:'北京'}), (b:Node{name:'上海'}) MERGE (a)-[r:`G1230`]->(b) RETURN a, b;
MATCH (a:Node{name:'上海'}), (b:Node{name:'苏州'}) MERGE (a)-[r:`G1230`]->(b) RETURN a, b;
MATCH (a:Node{name:'苏州'}), (b:Node{name:'南京'}) MERGE (a)-[r:`G27`]->(b) RETURN a, b;
MATCH (a:Node{name:'苏州'}), (b:Node{name:'南京'}) MERGE (a)-[r:`G26`]->(b) RETURN a, b;
MATCH (a:Node{name:'上海'}), (b:Node{name:'北京'}) MERGE (a)-[r:`G1231`]->(b) RETURN a, b;
MATCH (a:Node{name:'苏州'}), (b:Node{name:'上海'}) MERGE (a)-[r:`G1231`]->(b) RETURN a, b;
MATCH (a:Node{name:'南京'}), (b:Node{name:'杭州'}) MERGE (a)-[r:`G26`]->(b) RETURN a, b;
MATCH (a:Node{name:'北京'}), (b:Node{name:'徐州'}) MERGE (a)-[r:`G2620`]->(b) RETURN a, b;
MATCH (a:Node{name:'徐州'}), (b:Node{name:'淮安'}) MERGE (a)-[r:`G2620`]->(b) RETURN a, b;
MATCH (a:Node{name:'淮安'}), (b:Node{name:'宿迁'}) MERGE (a)-[r:`G2620`]->(b) RETURN a, b;
MATCH (a:Node{name:'宿迁'}), (b:Node{name:'南京'}) MERGE (a)-[r:`G2620`]->(b) RETURN a, b;
MATCH (a:Node{name:'南京'}), (b:Node{name:'杭州'}) MERGE (a)-[r:`G2620`]->(b) RETURN a, b;
```
### 运行本项目
直接运行com.example.demo.DemoApplication.main方法，之后访问Controller中的接口即可看到效果