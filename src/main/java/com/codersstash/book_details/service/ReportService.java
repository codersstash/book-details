package com.codersstash.book_details.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

@Service
public class ReportService {

    public JasperPrint generateReport(InputStream reportStream, Map param, JRBeanCollectionDataSource beanCollectionDataSource)  {
        try{
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
            return JasperFillManager.fillReport(jasperReport, param, beanCollectionDataSource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
