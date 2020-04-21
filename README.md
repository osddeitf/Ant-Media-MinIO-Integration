# Ant Media MinIO Integration
The workaround solution for integrate [MinIO](https://min.io) with [Ant Media Server](https://antmedia.io).

## How to use
- Build and package `.jar` using jdk-8 (newer version may work as well).
- Replace `lib/aws-java-sdk-s3-{version}.jar` with previous built `.jar`.
- Remove `lib/aws-java-sdk-core-{version}.jar` as well (to mitigate bean's class definition conflict).
- Insert the following to `webapps/{appName}/WEB-INF/red5-web.xml`:
```xml
<bean id="app.storageClient" class="io.antmedia.storage.AmazonS3StorageClient">
    <property name="accessKey" value="Enter your S3_ACCESS_KEY" />
    <property name="secretKey" value="Enter your S3_SECRET_KEY" />
    <property name="region" value="Enter your REGION_NAME e.g. eu-central-1" />
    <property name="storageName" value="Enter your BUCKET_NAME" />
</bean>
```
The workaround is that: instead of *region*, you must pass your whole url in `<property name="region">`, including scheme (*http* or *https*).

## Roadmap
- Reduce the risks of replacing and deleting the `.jar` files, by write another class instead of `io.antmedia.storage.AmazonS3StorageClient` and mocking `AmazonS3`.
- Migrate settings to `webapps/{appName}/WEB-INF/red5-web.properties`.
