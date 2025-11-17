package com.rantomah.boilerplate.core.helper;

import com.rantomah.boilerplate.core.util.FileUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileHelper {

    @Value("${app.upload.base-dir:uploads}")
    private String baseUploadDir;

    @Value("${app.upload.base-url:/files}")
    private String baseFilesUrl;

    public Map<String, Object> upload(String userId, MultipartFile file) throws IOException {
        Path userDir = Paths.get(baseUploadDir).resolve(userId);
        String savedFilename = FileUtils.uploadFile(file, userDir.toString());

        String contentType = file.getContentType();
        if (contentType == null || contentType.isBlank()) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        String url =
                String.format("%s/%s/%s", trimTrailingSlash(baseFilesUrl), userId, savedFilename);
        Map<String, Object> body = new HashMap<>();
        body.put("url", url);
        body.put("filetype", contentType);
        body.put("filename", file.getOriginalFilename());
        return body;
    }

    private String trimTrailingSlash(String path) {
        if (path == null || path.isEmpty()) {
            return "";
        }
        while (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }
}
