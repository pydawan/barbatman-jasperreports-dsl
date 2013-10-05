barbatman-jasperreports-dsl
===========================

* Domain Specific Language to sugar the way you populate and invoke a JRBeanCollectionDataSource report. *

It´s really simple to use. For sample, to generate an async report:

```
 new ReportCreator()
                    .report("report1") //report1.jasper should be present on classpath
                    .filledBy(dao.getCities())
                    .addParam(BBMConstants.FILE_GENERATION_KEY, "\u01\myAsyncReport.pdf") //output directory
                    .to(ReportFormat.PDF) //output format
                    .generateAsyncReport(); //future task invoked, your current thread is free and the report is being created!
```
I dont want to an async report! Ok:
```
ByteArrayOutputStream bytes = new ReportCreator()
                                                  .report("report1")
                                                  .filledBy(dao.getCities())
                                                  .to(ReportFormat.PDF)
                                                  .getBytes();
                                                  
//What you want to do with bytes?
```
You know, jasperreports way to create reports is so bored and non intuitive:
```
Map<String, Object> params = new HashMap<String, Object>();
JasperPrint jasperPrint = JasperFillManager.fillReport(JRLoader.getInputStream(JRLoader.getResource("report1.jasper")), params, new JRBeanCollectionDataSource(dao.getCities()));
JasperExportManager.exportReportToPdfFile(jasperPrint, "myReport.pdf");
```
Use Barbatman!
