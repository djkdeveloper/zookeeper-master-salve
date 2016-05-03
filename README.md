# zookeeper-master-salve
一个图片下载服务器  利用zookeeper  来实现master  slave  模式的图片下载

具体工作流程是：

启动的时候初始化 zookeeper 和activemq 分别是
ActiveMqInitServlet.java
ZookeeperInitServlet.java

监听mq的Record 主题 只要有消息进来 就选择一个最优的服务器进行消息的转发 给具体的slave 进行下载图片

所以涉及的技术有zookeeper  activemq 和hession rpc
