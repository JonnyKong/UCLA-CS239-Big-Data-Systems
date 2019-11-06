# UCLA-CS239-Big-Data-Systems

### Getting Started

1. Build [Apache Storm](https://github.com/apache/storm) following following [instructions](https://github.com/apache/storm/blob/master/DEVELOPER.md). You can skip the tests to save time:

```bash
$ mvn install -DskipTests=true
```

2. Install and Start ZooKeeper ([instructions](https://www.tutorialspoint.com/zookeeper/zookeeper_installation.htm)):

	```bash
   $ brew install zookeeper
   $ vim /usr/local/etc/zookeeper/zoo.cng
   $ zkServer start
  ```

3. Run example topologies defined in [storm-starter](https://github.com/apache/storm/tree/master/examples/storm-starter).

