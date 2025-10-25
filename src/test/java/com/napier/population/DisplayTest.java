package com.napier.population;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DisplayTest {

    private Display display;
    private String filename;

    @BeforeEach
    void setUp() {
        filename = "reports/DisplayTest.txt";
        display = new Display(filename);
    }

    @AfterEach
    void tearDown() {
        display.clearReportFile();
    }


}