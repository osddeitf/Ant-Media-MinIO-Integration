# Ant Media MinIO Integration
The workaround solution for integrate [MinIO](https://min.io) with [Ant Media Server](https://antmedia.io).

## How to use
- Build and package `.jar` using jdk-8 (newer version may work as well).
- Copy built `.jar` archive to `lib`.
- Download `minio-7.0.2-all.jar` and copy to `lib`. Note #Compatibility.
- Insert the following to `webapps/{appName}/WEB-INF/red5-web.xml`:
```xml
<bean id="app.storageClient" class="io.antmedia.storage.AmazonS3StorageClient">
    <property name="url" value="${storage.url}" />
    <property name="accessKey" value="${storage.accessKey}" />
    <property name="secretKey" value="${storage.secretKey}" />
    <property name="region" value="${storage.region}" />
    <property name="storageName" value="${storage.bucket}" />
</bean>
```
The workaround is that: instead of *region*, you must pass your whole url in `<property name="region">`, including scheme (*http* or *https*).
- Insert values to `webapps/{appName}/WEB-INF/red5-web.properties` (edit `MINIO_*` to your appropriate values):
```conf
storage.url=MINIO_URL
storage.accessKey=MINIO_ACCESSKEY
storage.secretKey=MINIO_SECRETKEY
storage.region=
storage.bucket=MINIO_BUCKET
```

## Compatibility
Due to some conflict of class definitions with the Ant Media Server implementation, some hidden bugs might arise.

If the above `minio-7.0.2-all.jar` may not work, try the following instead:
- `minio-7.0.2.jar`
- `okhttp-3.13.1.jar`
- `okio-1.17.2.jar`
- `simple-xml-2.7.1.jar`

Those are parts of the `minio-7.0.2` (not fat jar) dependencies, versioned exactly as defined in [pom.xml](https://github.com/minio/minio-java/blob/7.0.2/build.gradle#L50).

## Roadmap
- Currently, `storage.region` property is just placeholder. Consider support this in the future.
