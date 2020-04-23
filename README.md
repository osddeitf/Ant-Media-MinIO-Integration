# Ant Media MinIO Integration
The workaround solution for integrate [MinIO](https://min.io) with [Ant Media Server](https://antmedia.io).

## How to use
- Build and package `.jar` using jdk-8 (newer version may work as well).
- Copy built `.jar` archive to `lib`.
- Download `minio-7.0.2-all.jar` and copy to `lib`. See [compatibility](#Compatibility).
- Insert the following to `webapps/{appName}/WEB-INF/red5-web.xml`:
```xml
<bean id="app.storageClient" class="io.antmedia.storage.AmazonS3StorageClient">
    <property name="url" value="${storage.url}" />
    <property name="accessKey" value="${storage.accessKey}" />
    <property name="secretKey" value="${storage.secretKey}" />
    <property name="region" value="${storage.region}" />
    <property name="storageName" value="${storage.bucket}" />
    <property name="webhookURL" value="${storage.listenerHookURL}" >
</bean>
```
- Insert key-value pairs to `webapps/{appName}/WEB-INF/red5-web.properties` with key as the above (e.g. `storage.url`).
- Those keys inside `${}` must be present on `red5-web.properties` file for server boot up correctly.
- **Note**: You can also change the name of key in the above `red5-web.xml` sample to suit your need.

## Webhook
- You can specify `webhookURL` use `storage.listenerHookURL` like above, or leave this blank.
- For compatibility with current Ant Media Server implementation, that url will be `POST` with `Content-Type` be `application/x-www-form-urlencoded` (see [Webhook Integration](https://github.com/ant-media/Ant-Media-Server/wiki/Webhook-Integration)).
- Post data is currently, not fully compatible with Ant Media Server webhook:
    - `action`: `vodUploaded`
    - `vodName`: vod filename (i.e. for `.mp4` files inside app's `streams/` folder, only the filename, not include `streams/` prefix).
## Compatibility
Due to some conflict of class definitions with the Ant Media Server implementation, some hidden bugs might arise.

If the above `minio-7.0.2-all.jar` (fat jar) may not work, try the following instead:
- `minio-7.0.2.jar`
- `okhttp-3.13.1.jar`
- `okio-1.17.2.jar`
- `simple-xml-2.7.1.jar`

Those are parts of the `minio-7.0.2` (not fat jar) dependencies, versioned exactly as defined in [pom.xml](https://github.com/minio/minio-java/blob/7.0.2/build.gradle#L50).

You can also find these files all together by conveniently look at my docker repository: https://github.com/osddeitf/Ant-Media-Docker 

## Roadmap
- Currently, `storage.region` property is just placeholder. Consider support this in the future.
