package org.customers.system.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileStorageServiceTest {

    @Autowired
    private StorageService storageService;

//    private File testFile;
    private MockMultipartFile testFile;

    private static final String FILE_NAME = "tempfile.tmp";


    private static final String FILE_EXTENSION = "tmp";
    private static final String FILE_CONTENT = "Temporary content";
    private static final String USER = "testUser";

    @Before
    public void createFile() {
        testFile = new MockMultipartFile(FILE_NAME, FILE_CONTENT.getBytes());
    }

    @Test
    public void shouldSaveFile() throws Exception {
        //when
        String storedFileName = storageService.store(testFile, USER);

        // then
        assertEquals(String.join("_", USER, testFile.getName()), storedFileName);
    }

    @Test
    public void shouldLoadSavedFile() throws Exception {
        // given
        storageService.store(testFile, USER);

        // when
        String fileName = String.join("_", USER, testFile.getName());
        Optional<Resource> optional = storageService.load(fileName);

        assertTrue(optional.isPresent());

        // then
        File loadedFile = optional.get().getFile();
        String fileContent = Files.lines(loadedFile.toPath(), StandardCharsets.UTF_8).findAny().get();
        assertEquals(FILE_CONTENT, fileContent);
    }

    @Test()
    public void shouldThrowExceptionWhenFileDoesntExist() throws Exception {
        Optional<Resource> optionalResource = storageService.load("notExistingFile.temp");
        assertFalse(optionalResource.isPresent());
    }

}
