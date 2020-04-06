package ru.cash.core.util;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import liquibase.integration.spring.SpringLiquibase;

/**
 * Multiple liquibase changelog support class.
 * @author emelnikov
 */
public class MultipleChangeLogSupport implements ApplicationContextAware {

    private String[] changeLogs;
    private DataSource dataSource;
    private ApplicationContext applicationContext;

    /**
     * Applies changelogs to database.
     * @throws IOException if error rise
     */
    @PostConstruct
    private void initializeLiqubaseBeans() throws IOException {
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();

        for (String changeLog : changeLogs) {
            for (Resource resource : applicationContext.getResources(changeLog)) {
                SpringLiquibase liqubaseBean = new SpringLiquibase();
                liqubaseBean.setDataSource(dataSource);
                liqubaseBean.setChangeLog(resource.getURI().toString());
                beanFactory.initializeBean(liqubaseBean, SpringLiquibase.class.getSimpleName());
                beanFactory.destroyBean(liqubaseBean);
            }
        }
        beanFactory.destroyBean(this);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * Sets changelogs.
     * @param changeLogs changelogs
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public void setChangeLogs(String[] changeLogs) {
        this.changeLogs = changeLogs;
    }

    /**
     * Sets {@link DataSource} instance.
     * @param dataSource {@link DataSource} instance
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
