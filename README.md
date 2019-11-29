# UCLA-CS239-Big-Data-Systems

### Getting Started

##### Install Apache Storm

1. Build [Apache Storm](https://github.com/apache/storm) following following [instructions](https://github.com/apache/storm/blob/master/DEVELOPER.md). You can skip the tests to save time:
```bash
$ mvn install -DskipTests=true
```
2. (Requred for remote cluster) Install and Start ZooKeeper ([instructions](https://www.tutorialspoint.com/zookeeper/zookeeper_installation.htm)):

```bash
$ brew install zookeeper
$ vim /usr/local/etc/zookeeper/zoo.cfg
$ zkServer start
```

3. (Requred for remote cluster) Create and run a Storm Distribution

```bash
$ brew install pgp && gpg --gen-key   # If you haven't already, create PGP key-pair
$ export GPG_TTY=$(tty)               # Make gpg happy
# Create Storm Distribution (Storm can only execute in release version)
$ cd <STORM_SRC>/storm-dist/binary && mvn package
$ cd <STORM_SRC>/storm-dist/binary/final-package/target
$ tar -xzvf apache-storm-2.2.0-SNAPSHOT.tar.gz && cd apache-storm-2.2.0-SNAPSHOT
```

4. (Optional) Run example topologies defined in [storm-starter](https://github.com/apache/storm/tree/master/examples/storm-starter).

```bash
# Update config (see instructions)
$ vim conf/storm.yaml
# Build examples
$ cd <STORM_SRC>/examples/storm-starter && mvn package
# Submit example topologies remotely
$ <STORM_SRC>/storm-dist/binary/final-package/target/apache-storm-2.2.0-SNAPSHOT/bin/storm jar examples/storm-starter/target/storm-starter-2.2.0-SNAPSHOT.jarorg.apache.storm.starter.ExclamationTopology
# Submit example topologies locally
$ <STORM_SRC>/storm-dist/binary/final-package/target/apache-storm-2.2.0-SNAPSHOT/bin/storm jar examples/storm-starter/target/storm-starter-2.2.0-SNAPSHOT.jarorg.apache.storm.starter.ExclamationTopology -local
```

* [Local Mode](https://github.com/apache/storm/blob/master/docs/Local-mode.md)

5. Run example topology in this repository

```bash
$ mvn install
$ mvn exec:java
```

##### Install CouchDB: 

1. [GUI version](https://couchdb.apache.org/#download)

##### Useful CouchDB Commands:
```bash
# 1. Create database baseball
$ curl -X PUT http://127.0.0.1:5984/baseball
# 2. Delete database baseball
$ curl -X DELETE http://127.0.0.1:5984/baseball
# 3. Lookup
$ curl http://127.0.0.1:5984/baseball/00a271787f89c0ef2e10e88a0c0001f4
# 4. Insert
$ curl -H 'Content-Type: application/json' \
    -X POST http://127.0.0.1:5984/baseball \
    -d '{"key": "value"}'
# 5. Update
$ curl -H 'Content-Type: application/json' \
    -X PUT http://127.0.0.1:5984/baseball/00a271787f89c0ef2e10e88a0c0001f4
    -d '{"_rev": "1-59414e77c768bc202142ac82c2f129de", "key": "value"}'
# 6. Return all docs in a database
$ curl http://127.0.0.1:5984/baseball/_all_docs
```