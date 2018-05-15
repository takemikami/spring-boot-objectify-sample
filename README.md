# spring-boot-objectify-sample


## prerequisite

Install datastore emulator.

```
gcloud components install cloud-datastore-emulator
```


## run with datastore emulator

Start datastore emulator.

```
gcloud beta emulators datastore start --host-port=localhost:8484
```

Start spring-boot web application.

```
gradle bootRun
```


## run with datastore emulator and memcache

Start datastore emulator.

```
gcloud beta emulators datastore start --host-port=localhost:8484
```

Start spring-boot web application.

```
gradle appengineRun
```
