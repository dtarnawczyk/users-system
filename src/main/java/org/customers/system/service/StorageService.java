package org.customers.system.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface StorageService {

    String store(MultipartFile file, String userLogin) throws Exception;

    Optional<Resource> load(String fileName) throws Exception;

}
