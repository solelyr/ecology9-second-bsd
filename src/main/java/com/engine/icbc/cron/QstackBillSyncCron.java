package com.engine.icbc.cron;

import com.engine.common.util.ServiceUtil;
import com.engine.icbc.service.EnterpriseBillService;
import com.engine.icbc.service.impl.EnterpriseBillServiceImpl;
import weaver.interfaces.schedule.BaseCronJob;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @DESCRIPTION:
 * @USER: solelyr
 * @DATE: 2026/9/18 21:25
 */
public class QstackBillSyncCron extends BaseCronJob {
    @Override
    public void execute() {
        LocalDate now = LocalDate.now();
        LocalDate nextYearDate = LocalDate.now().plusYears(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateStr = now.format(formatter);
        String nextYearStr = nextYearDate.format(formatter);
        getService().QstackBillSync(dateStr, nextYearStr);
    }

    private EnterpriseBillService getService(){
        return ServiceUtil.getService(EnterpriseBillServiceImpl.class);
    }

}
