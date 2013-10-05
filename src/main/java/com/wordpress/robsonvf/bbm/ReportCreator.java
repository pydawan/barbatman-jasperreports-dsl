package com.wordpress.robsonvf.bbm;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.util.JRLoader;

public class ReportCreator {

    private static String fixJasperFile(String file) {
        if (file == null) {
            throw new RuntimeException("file cannot be null");
        }
        file = file.trim().toLowerCase();
        if (!file.endsWith(".jasper")) {
            file = file + ".jasper";
        }
        return file;
    }

    public ReportDataSet report(String jasper) {
        try {
            URL url = JRLoader.getResource(fixJasperFile(jasper));
            return new ReportDataSet(JRLoader.getInputStream(url));
        } catch (JRException ex) {
            Logger.getLogger(ReportCreator.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Cannot load the jasper file", ex);
        }

    }

    public ReportDataSet report(URL url) {
        try {
            return new ReportDataSet(JRLoader.getInputStream(url));
        } catch (Exception ex) {
            Logger.getLogger(ReportCreator.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("Cannot load the jasper file", ex);
        }

    }
}
