package org.uarizona.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtils {

    private static Path foundFile;

    public static String saveFile(String fileName, MultipartFile file) {

        String uploadFileName;
        try {
            Path uploadDirectory = Paths.get("upload-path");

            if (!Files.exists(uploadDirectory)) {
                Files.createDirectory(uploadDirectory);
            }
            uploadFileName = fileName + "-" + RandomStringUtils.randomAlphanumeric(8);

            try (InputStream inputStream = file.getInputStream()) {
                Path uploadedFilePath = uploadDirectory.resolve(uploadFileName);
                Files.copy(inputStream, uploadedFilePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("Could not save file: " + fileName, ioe);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return uploadFileName;
    }

    public static Resource getFileResource(String fileCode) throws IOException {
        Path dirPath = Paths.get("Files-Upload");
        Files.list(dirPath).forEach(file -> {
            if (file.getFileName().toString().startsWith(fileCode)) {
                foundFile = file;
                return;
            }
        });
        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        }
        return null;
    }
}
