package com.ivaaan.seen.uploads;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

   

    public static void validatePost(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Post image is required");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Invalid post image format");
        }

        long maxSize = 5 * 1024 * 1024; // 5 MB
        if (file.getSize() > maxSize) {
            throw new IllegalArgumentException("Post image is too large (max 5MB)");
        }
    }

     // TODO Compress files
    public static String uploadUserPost(MultipartFile newUserPost, Long idUser, Long idPost) {
        try {
            Path uploadDir = Paths.get("uploads/users/" + idUser + "/posts/");
            Files.createDirectories(uploadDir);

            String contentType = newUserPost.getContentType();

            String extension = contentType.substring(contentType.indexOf("/") + 1);

            String filename = idPost + "." + extension;
            Path path = uploadDir.resolve(filename);

            Files.copy(newUserPost.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            return path.toString();

        } catch (IOException e) {
            throw new RuntimeException("Error uploading post", e);
        }
    }

    public static String uploadUserPhotoProfile(MultipartFile newUserPhotoProfile, Long idUser) {
        try {
            Path uploadDir = Paths.get("uploads/users/" + idUser + "/profile/");
            Files.createDirectories(uploadDir);

            String contentType = newUserPhotoProfile.getContentType();

            String extension = contentType.substring(contentType.indexOf("/") + 1);
            String filename = "profile." + extension;

            Path path = uploadDir.resolve(filename);
            Files.copy(
                    newUserPhotoProfile.getInputStream(),
                    path,
                    StandardCopyOption.REPLACE_EXISTING);

            return path.toString();

        } catch (IOException e) {
            throw new RuntimeException("Error uploading user photo profile", e);
        }
    }

}
