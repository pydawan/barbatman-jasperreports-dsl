package com.wordpress.robsonvf.bbm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author rf49961
 */
public class ReportBuilder {

    private ReportFormat format;
    private InputStream jasperName;
    private Map<String, Object> params;
    private JRBeanCollectionDataSource fillList;

    public ReportBuilder(ReportFormat format, InputStream jasperName, Map<String, Object> params, JRBeanCollectionDataSource fillList) {
        this.format = format;
        this.jasperName = jasperName;
        this.params = params;
        this.fillList = fillList;
    }

    public ByteArrayOutputStream getBytes() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            JasperPrint print = JasperFillManager.fillReport(jasperName, params, fillList);
            JRExporter exporter = format.getExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            exporter.exportReport();

        } catch (Exception e) {
            throw new RuntimeException("Error getting the stream", e);
        }
        return out;
    }

    public OutputStream getOutputStream(OutputStream out) {
        try {
            JasperPrint print = JasperFillManager.fillReport(jasperName, params, fillList);
            JRExporter exporter = format.getExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            exporter.exportReport();
        } catch (Exception e) {
            throw new RuntimeException("Error getting the stream", e);
        }
        return out;
    }

    /**
     * Gera um arquivo de relatório de forma assíncrona (em uma thread separada)
     * Utiliza como chave a constante BarbatmanConstants.FILE_GENERATION_KEY
     * onde o arquivo de destino deve ser o valor.
     * 
     * TODO: possibilitar ao tratamento da thread posterior a execução (alguem tem que saber quando o processo termina)
     * 
     */
    public void generateAsyncReport() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        FutureTask<Integer> futureOne = new FutureTask<Integer>(new Callable<Integer>() {
            public Integer call() throws Exception {
                try {
                    JasperPrint print = JasperFillManager.fillReport(jasperName, params, fillList);
                    JRExporter exporter = format.getExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                    exporter.setParameter(JRExporterParameter.OUTPUT_FILE, new File(params.get(BBMConstants.FILE_GENERATION_KEY).toString()));
                    exporter.exportReport();
                } catch (Exception e) {
                    Logger.getLogger(ReportBuilder.class.getName()).log(Level.SEVERE, null, e);
                    return -1;
                }
                return 1;
            }
        });
        executor.execute(futureOne);
        executor.shutdown();
    }
}
