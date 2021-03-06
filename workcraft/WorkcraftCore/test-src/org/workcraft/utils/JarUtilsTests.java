package org.workcraft.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class JarUtilsTests {

    @Test
    public void resourcePathTest() throws IOException, URISyntaxException {
        Assertions.assertTrue(JarUtils.getResourcePaths("non-existent").isEmpty());
        Assertions.assertFalse(JarUtils.getResourcePaths("scripts/").isEmpty());
    }

}
