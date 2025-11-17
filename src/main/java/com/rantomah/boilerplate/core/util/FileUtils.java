package com.rantomah.boilerplate.core.util;

import com.rantomah.boilerplate.core.exception.GenericException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

    private static final long MAX_FILE_SIZE = 10L * 1024L * 1024L; // 10MB
    private static final Set<String> ALLOWED_FILE_TYPES =
            new HashSet<>(
                    Arrays.asList(
                            "image/jpeg",
                            "image/png",
                            "image/gif",
                            "application/pdf",
                            "application/msword",
                            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"));

    public static List<String> readListFromFile(String filePath) {
        try {
            File file = ResourceUtils.getFile(filePath);
            return Files.readAllLines(file.toPath()).stream()
                    .filter(line -> !line.trim().isEmpty())
                    .toList();
        } catch (IOException e) {
            throw new GenericException("Failed to read lines from file: " + filePath);
        }
    }

    public static List<String> readNonEmptyLinesFromFile(String filePath) {
        try {
            File file = extractFileFromPath(filePath);
            return extractNonEmptyLinesFromFile(file);
        } catch (IOException e) {
            throw new GenericException("Failed to read lines from file");
        }
    }

    private static File extractFileFromPath(String filePath) throws FileNotFoundException {
        return ResourceUtils.getFile(filePath);
    }

    public static List<String> extractNonEmptyLinesFromFile(File file) throws IOException {
        return Files.readAllLines(file.toPath()).stream()
                .filter(line -> !line.trim().isEmpty())
                .toList();
    }

    public static List<String> extractNonEmptyLinesFromInputStream(InputStream inputStream)
            throws IOException {
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().filter(line -> !line.trim().isEmpty()).toList();
        }
    }

    public static boolean deleteFile(String filename, String directory) {
        try {
            Path filePath = Paths.get(directory).resolve(filename);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            return false;
        }
    }

    private static String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
    }

    public static String uploadFile(MultipartFile file, String uploadDir) throws IOException {
        validateFile(file);

        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String originalFilename =
                StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = getFileExtension(originalFilename);
        String newFilename = UUID.randomUUID().toString() + "." + fileExtension;

        // Save file
        Path filePath = uploadPath.resolve(newFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return newFilename;
    }

    public static File createTempFile(MultipartFile file) throws IOException {
        String originalFilename =
                StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String extension = getFileExtension(originalFilename);

        File tempFile = File.createTempFile("temp-", "." + extension);
        Files.copy(file.getInputStream(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        tempFile.deleteOnExit();

        return tempFile;
    }

    public static boolean fileExists(String filename, String directory) {
        Path filePath = Paths.get(directory).resolve(filename);
        return Files.exists(filePath);
    }

    public static long getFileSize(String filename, String directory) {
        try {
            Path filePath = Paths.get(directory).resolve(filename);
            return Files.size(filePath);
        } catch (IOException e) {
            return -1L;
        }
    }

    private static void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        // Check filename
        if (filename.contains("..")) {
            throw new IllegalArgumentException("Filename contains invalid path sequence");
        }

        // Check file size
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size exceeds maximum limit of 10MB");
        }

        // Check file type
        String contentType = file.getContentType();
        if (!ALLOWED_FILE_TYPES.contains(contentType)) {
            throw new IllegalArgumentException(
                    "File type not allowed. Supported types: JPEG, PNG, GIF, PDF, DOC, DOCX");
        }
    }
}
