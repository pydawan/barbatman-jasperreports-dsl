package com.wordpress.robsonvf.bbm;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author rf49961
 */
public class ReportDataSet {
    
    private InputStream jasperName;
    private Map<String, Object> params = new HashMap<String, Object>();
    private JRBeanCollectionDataSource fillList;
    
    public ReportDataSet(InputStream jasperName) {
        this.jasperName = jasperName;
    }
    
    public ReportDataSet addParam(String key, Object value) {
        params.put(key, value);
        return this;
    }
    
    public ReportDataSet addParams(Map<String, Object> params) {
        this.params.putAll(params);
        return this;
    }
    
    public <T> ReportDataSet filledBy(List<T> fillList) {
        this.fillList = new JRBeanCollectionDataSource(fillList);
        return this;
    }
    
    public ReportBuilder to(ReportFormat format) {
        return new ReportBuilder(format, jasperName, params, fillList);
    }
}
