package org.customers.system.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Files;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileStorageServiceTest {

    @Autowired
    private StorageService storageService;

//    private File testFile;
    private MockMultipartFile testFile;

    private static final String FILE_NAME = "tempfile.tmp";
    private static final String FILE_CONTENT = "Temporary content";
    private static final String USER = "testUser";

    @Before
    public void createFile() {
        testFile = new MockMultipartFile(FILE_NAME, FILE_CONTENT.getBytes());
    }

    @Repeat(10)
    @Test
    public void shouldSaveAndLoadFile() throws Exception {
        //when
        String storedFileName = storageService.store(testFile, USER);

        // then
        assertEquals(String.join("_", USER, testFile.getName()), storedFileName);

        Optional<Resource> resource = storageService.load(storedFileName);
        Files.lines(resource.get().getFile().toPath()).forEach(line ->
                assertEquals(line, FILE_CONTENT)
        );

    }

    @Test()
    public void shouldThrowExceptionWhenFileDoesntExist() throws Exception {
        Optional<Resource> optionalResource = storageService.load("notExistingFile.temp");
        assertFalse(optionalResource.isPresent());
    }

}
