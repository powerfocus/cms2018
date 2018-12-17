package org.py.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SetupTest {
    @Autowired
    private Setup setup;
    @Test
    public void install() throws IOException {
        setup.install();
    }
    @Test
    public void readDefaultList() throws IOException {
        setup.readDefaultList().forEach(System.out::println);
    }
}