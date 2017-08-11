package org.customers.system.web.config.localFileStorage;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.customers.system.service.StorageService;
import org.customers.system.web.config.resource.PictureProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Optional;

@Service
@Slf4j
public class FileStorageService implements StorageService {

    private static final String NAME_JOINER = "_";

    private final Resource picturesResource;

    public FileStorageService(PictureProperties pictureProperties) {
        this.picturesResource = pictureProperties.getUploadPath();
    }

    @Override
    public String store(MultipartFile file, String userLogin) throws IOException {
        return storeFileInFileSystem(file, userLogin);
    }

    @Override
    public Optional<Resource> load(String fileName) throws IOException {
        String filePath = String.join(File.separator, this.picturesResource.getFile().getPath(), fileName);
        Resource resource = new FileSystemResource(filePath);
        if(resource.exists() || resource.isReadable()) {
            return Optional.of(resource);
        } else {
            log.info("Could not find file: " + filePath);
        }
        return Optional.empty();
    }

    private String storeFileInFileSystem(MultipartFile file, String username) throws IOException {
        if (file.isEmpty()) {
            log.info("Failed to store empty file " + file.getOriginalFilename());
            throw new IOException();
        } else {
            String orginalFilename = file.getOriginalFilename().isEmpty() ? file.getName() : file.getOriginalFilename(); 
            String fileName = createFileName(orginalFilename, username);
            File newFile = new File(this.picturesResource.getFile(), fileName);
            if(newFile.exists())
                newFile.delete();
            newFile.createNewFile();
            InputStream in = file.getInputStream();
            OutputStream out = new FileOutputStream(newFile, false);
            IOUtils.copy(in, out);
            return newFile.getName();
        }
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }

    private String createFileName(String fileName, String userName) {
        return String.join(NAME_JOINER, userName, fileName);
    }
}
