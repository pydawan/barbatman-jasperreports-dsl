package com.wordpress.robsonvf.bbm;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 *
 * @author rf49961
 */
public enum ReportFormat {

    XLS {
        @Override
        public JRExporter getExporter() {
            JRExporter exporter = new JRXlsxExporter();
            exporter.setParameter(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
            exporter.setParameter(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, false);
            exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
            return exporter;
        }
    },
    HTML {
        public JRExporter getExporter() {
            JRExporter exporter = new JRHtmlExporter();
            return exporter;
        }
    },
    PDF {
        @Override
        public JRExporter getExporter() {
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET, 30);
            return exporter;
        }
    };

    abstract JRExporter getExporter();
}
