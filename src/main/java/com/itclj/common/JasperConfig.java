package com.itclj.common;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsViewResolver;

import javax.sql.DataSource;

@Configuration
public class JasperConfig extends WebMvcConfigurerAdapter {

    private final static String REPORT_DATA_KEY = "datasource";
    private final static String PATH_KEY = "classpath:jrxml/";
    private final static String TYPE_KEY = ".jrxml";
    private final static String VIEW_KEY = "report";

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public JasperReportsViewResolver getJasperReportsViewResolver(){
        JasperReportsViewResolver resolver = new JasperReportsViewResolver();
        resolver.setPrefix(PATH_KEY); //resource文件夹下放模板的路径
        resolver.setSuffix(TYPE_KEY); //模板文件的类型，这里选用jrxml而不是编译之后的jasper

        //JasperReportsMultiFormatView定义了ReportDataKey，这里给定key为datasource，后面controller的时候会用到
        resolver.setReportDataKey(REPORT_DATA_KEY);
        resolver.setViewNames("*" + VIEW_KEY + "*"); //视图名称，模板名称需要符合 *你定义的key* 如*Report*
        resolver.setViewClass(JasperReportsMultiFormatView.class); //视图类
        resolver.setOrder(0); //顺序为第一位
        return resolver;
    }
}
