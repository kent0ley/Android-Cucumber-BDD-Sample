package com.codemate.booklibrary;

import junit.framework.TestCase;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(glue = "com.codemate.booklibrary",  features = "features", plugin = {"pretty", "junit:Folder_Name/cucumber.xml"})
public class CucumberTests extends TestCase {
}