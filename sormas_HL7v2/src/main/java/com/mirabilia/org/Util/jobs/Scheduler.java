/*
 * Copyright (c) 2021, Augustus otu
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.mirabilia.org.Util.jobs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author Augustus otu
 */
public class Scheduler implements ServletContextListener {

    static org.quartz.Scheduler sch;

    public static void shutdown() {
        try {
            sch.shutdown();
        } catch (SchedulerException ex) {
            Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void Start(){
         try {

            JobDetail job = JobBuilder.newJob(ReportJob.class)
                    .withIdentity("reportJob")
                    .build();

            String expression = getTitle("reportJobExpression");
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("crontrigger", "crontriggergroup1")
                    .withSchedule(CronScheduleBuilder.cronSchedule(expression))
                    .build();

            SchedulerFactory schFactory = new StdSchedulerFactory();
            sch = schFactory.getScheduler();
            sch.start();
            sch.scheduleJob(job, cronTrigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        //To change body of generated methods, choose Tools | Templates.
        try {

            JobDetail job = JobBuilder.newJob(ReportJob.class)
                    .withIdentity("reportJob")
                    .build();

            String expression = getTitle("reportJobExpression");
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("crontrigger", "crontriggergroup1")
                    .withSchedule(CronScheduleBuilder.cronSchedule(expression))
                    .build();

            SchedulerFactory schFactory = new StdSchedulerFactory();
            sch = schFactory.getScheduler();
            sch.start();
            sch.scheduleJob(job, cronTrigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        try {
            sch.shutdown();
        } catch (SchedulerException ex) {
            Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getTitle(String key) {

        try {
            String resourceName = "job.properties";
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Properties props = new Properties();
            InputStream resourceStream = loader.getResourceAsStream(resourceName);
            props.load(resourceStream);

            Enumeration enuKeys = props.keys();
            while (enuKeys.hasMoreElements()) {
                String value = props.getProperty(key);
                return value;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
