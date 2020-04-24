package com.osddeitf.antmedia;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.commons.lang3.exception.ExceptionUtils;

import io.antmedia.storage.StorageClient;
import io.minio.ErrorCode;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MinioStorageClient extends StorageClient {

    private MinioClient minioClient;
    private String url;
    private String webhookURL = null;

    protected OkHttpClient httpClient;
    protected static Logger logger = Logger.getLogger(MinioStorageClient.class.getName());
    private static final String ACTION_NAME = "vodUploaded";

    public MinioStorageClient() {
        this.httpClient = new OkHttpClient.Builder()
            .callTimeout(10, TimeUnit.SECONDS)
            .build();
    }

    private MinioClient getMinioClient() throws InvalidEndpointException, InvalidPortException {
        if (minioClient == null) {
            minioClient = new MinioClient(this.getUrl(), this.getAccessKey(), this.getSecretKey());
        }
        return minioClient;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebhookURL() {
        return webhookURL;
    }

    public void setWebhookURL(String url) {
        this.webhookURL = url;
    }

    /** Log utilities */
    private void info(String message, Object ...args) {
        logger.info(String.format(message, args));
    }

    private void error(String message, Object ...args) {
        logger.log(java.util.logging.Level.SEVERE, String.format(message, args));
    }

    /** Webhook notify */
    private void notifyWebhook(String filename) {
        if (webhookURL == null || webhookURL.isEmpty()) return;

        String httpURL = webhookURL.matches("https?://.*") ? webhookURL : "https://" + webhookURL;
        info("Webhook `%s` invoking at %s with parameters: %s", ACTION_NAME, httpURL, filename);
        
        // Omit `id` and `vodId`
        RequestBody body = new FormBody.Builder()
            .addEncoded("action", ACTION_NAME)
            .addEncoded("vodName", filename)
            .build();

        Request request = new Request.Builder()
            .url(httpURL)
            .post(body)
            .build();
        
        try {
            Response response = httpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                info("Webhook `%s` %s success", ACTION_NAME, filename);
            }
            else {
                error("Webhook `%s` %s failed with status code %d", ACTION_NAME, filename, response.code());
            }
            response.close();
        }
        catch (IOException exception) {
            error("Webhook `%s` %s failed - Network error", ACTION_NAME, filename);
        }
    }

    /** Interface declarations */
    @Override
    public boolean fileExist(String filename, FileType type) {
        try {
            getMinioClient().statObject(getStorageName(), filename);
            return true;
        }
        catch (ErrorResponseException e) {
            if (e.errorResponse().errorCode() == ErrorCode.NO_SUCH_OBJECT)
                return false;
            error(e.toString());
        }
        catch (Exception e) {
            error(ExceptionUtils.getStackTrace(e));
        }
        return false;
    }

    @Override
    public void delete(String filename, FileType type) {
        try {
            getMinioClient().removeObject(getStorageName(), filename);
        }
        catch (Exception e) {
            error(ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public void save(File file, FileType type) {
        try {
            info("Mp4 `%s` upload to MinIO has started", file.getName());

            InputStream inputStream = new FileInputStream(file);
            getMinioClient().putObject(
                getStorageName(),
                type.getValue() + "/" + file.getName(),
                new FileInputStream(file),
                new PutObjectOptions(inputStream.available(), 10*1024*1024)
            );
            inputStream.close();
            Files.delete(file.toPath());
            notifyWebhook(file.getName());

            info("File `%s` uploaded to MinIO", file.getName());
        }
        catch (Exception e) {
            error(ExceptionUtils.getStackTrace(e));
        }
    }
}
