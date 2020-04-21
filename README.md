# Ant Media MinIO Integration
The workaround solution for integrate [MinIO](https://min.io) with [Ant Media Server](https://antmedia.io).

## How to use
- Build and package `.jar` using jdk-8 (newer version may work as well).
- Replace `lib/aws-java-sdk-s3-{version}.jar` with previous built `.jar`.
- Remove `lib/aws-java-sdk-core-{version}.jar` as well (to mitigate bean's class definition conflict).
- Download `minio` version `7.0.1` and copy to `lib`.
- Insert the following to `webapps/{appName}/WEB-INF/red5-web.xml`:
```xml
<bean id="app.storageClient" class="io.antmedia.storage.AmazonS3StorageClient">
    <property name="accessKey" value="${storage.accessKey}" />
    <property name="secretKey" value="${storage.secretKey}" />
    <property name="region" value="${storage.url}" />
    <property name="storageName" value="${storage.bucket}" />
</bean>
```
The workaround is that: instead of *region*, you must pass your whole url in `<property name="region">`, including scheme (*http* or *https*).
- Insert values to `webapps/{appName}/WEB-INF/red5-web.properties` (edit `MINIO_*` to your appropriate values):
```conf
storage.url=<MINIO_URL>
storage.accessKey=<MINIO_ACCESSKEY>
storage.secretKey=<MINIO_SECRETKEY>
storage.bucket=<MINIO_BUCKET>
```

## Roadmap
- Reduce the risks of replacing and deleting the `.jar` files, by write another class instead of `io.antmedia.storage.AmazonS3StorageClient` and mocking `AmazonS3`.
