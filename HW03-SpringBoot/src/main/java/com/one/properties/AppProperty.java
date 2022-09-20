package com.one.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "app")
public class AppProperty {

    private Locale locale;
    private CsvProperty csvProperty;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public CsvProperty getCsvProperty() {
        return csvProperty;
    }

    public void setCsvProperty(CsvProperty csvProperty) {
        this.csvProperty = csvProperty;
    }

    public static class CsvProperty {

        private String file;
        private String directory;

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getDirectory() {
            return directory;
        }

        public void setDirectory(String directory) {
            this.directory = directory;
        }
    }
}
