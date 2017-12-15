package imes.core;

import imes.job.QHEngine;
import imes.job.QHReminder;
import imes.job.RfcBapiGoodsmvtCreate311;
import imes.job.RfcBapiGoodsmvtCreate344;
import imes.job.RfcBapiGoodsmvtCreatePoGoodsReceipt;
import imes.job.RfcBapiPoChageNetPrice;
import imes.job.RfcBapiPoChangeEket;
import imes.job.RfcZConfirmationNewUpd;
import imes.job.RfcZmmMigoCreatePoReceiptSap;
import imes.job.RfcZmmModifyPObyNewInfoRec;
import imes.job.RfcZmmModifyPObyNewInfoRecAfterBooking;
import imes.job.SendCancelPoToSupplier;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.log4j.Logger;

@WebListener
public class JobServer implements ServletContextListener {

	private ScheduledExecutorService scheduler;
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("JobServer Startup...");
		
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler = Executors.newScheduledThreadPool(7);

//		scheduler.scheduleWithFixedDelay(new RfcBapiPoChageNetPrice(event.getServletContext()), 0, 4, TimeUnit.HOURS);

		scheduler.scheduleWithFixedDelay(new QHEngine(event), 0, 5, TimeUnit.MINUTES);
		scheduler.scheduleAtFixedRate(new QHReminder(event), 0, 60, TimeUnit.MINUTES);
		scheduler.scheduleWithFixedDelay(new RfcZmmModifyPObyNewInfoRec(event.getServletContext()), 0, 10, TimeUnit.MINUTES);
		scheduler.scheduleWithFixedDelay(new RfcZConfirmationNewUpd(event.getServletContext()), 0, 10, TimeUnit.MINUTES);
//		scheduler.scheduleWithFixedDelay(new RfcBapiPoChangeEket(event.getServletContext()), 0, 4, TimeUnit.HOURS);
		scheduler.scheduleWithFixedDelay(new RfcBapiPoChageNetPrice(event.getServletContext()), 0, 4, TimeUnit.HOURS);

		//RfcBapiGoodsmvtCreatePoGoodsReceipt
		scheduler.scheduleWithFixedDelay(new RfcBapiGoodsmvtCreatePoGoodsReceipt(event.getServletContext()), 0, 10, TimeUnit.MINUTES);

//		scheduler.scheduleWithFixedDelay(new RfcZmmMigoCreatePoReceiptSap(event.getServletContext()), 0, 10, TimeUnit.MINUTES);
//		scheduler.scheduleWithFixedDelay(new RfcZmmModifyPObyNewInfoRecAfterBooking(event.getServletContext()), 0, 10, TimeUnit.MINUTES);

//		scheduler.scheduleWithFixedDelay(new SendCancelPoToSupplier(event), 0, 120, TimeUnit.MINUTES);

//		scheduler.scheduleWithFixedDelay(new RfcBapiGoodsmvtCreate311(event.getServletContext()), 0, 4, TimeUnit.HOURS);
//		scheduler.scheduleWithFixedDelay(new RfcBapiGoodsmvtCreate344(event.getServletContext()), 0, 4, TimeUnit.HOURS);

	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		scheduler.shutdown();
		logger.info("JobServer Shutdown...");
	}

}
