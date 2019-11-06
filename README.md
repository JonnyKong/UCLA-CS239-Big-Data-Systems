# UCLA-CS239-Big-Data-Systems

### Getting Started

1. Build [Apache Storm](https://github.com/apache/storm) following following [instructions](https://github.com/apache/storm/blob/master/DEVELOPER.md). You can skip the tests to save time:
```bash
$ mvn install -DskipTests=true
```
2. Install and Start ZooKeeper ([instructions](https://www.tutorialspoint.com/zookeeper/zookeeper_installation.htm)):

```bash
$ brew install zookeeper
$ vim /usr/local/etc/zookeeper/zoo.cfg
$ zkServer start
```

3. Create a Storm Distribution

```bash
$ brew install pgp && gpg --gen-key		# If you haven't already, create PGP key-pair
$ export GPG_TTY=$(tty)								# Make gpg happy
# Create Storm Distribution (Storm can only execute in release version)
$ cd <STORM_SRC>/storm-dist/binary && mvn package
```

4. Run example topologies defined in [storm-starter](https://github.com/apache/storm/tree/master/examples/storm-starter). [(Instructions)](https://www.tutorialspoint.com/apache_storm/apache_storm_installation.htm)

```bash
$ cd <STORM_SRC>/storm-dist/binary/final-package/target
$ tar -xvf apache-storm-2.2.0-SNAPSHOT.tar.gz && cd apache-storm-2.2.0-SNAPSHOT
# Configure and run
$ vim conf/storm.yaml				# Update config (see instructions)
$ ./bin/storm nimbus				# Start the Nimbus
$ ./bin/storm supervisor		# Start the Supervisor
$ ./bin/storm ui						# Start the UI (localhost:5000)
```
