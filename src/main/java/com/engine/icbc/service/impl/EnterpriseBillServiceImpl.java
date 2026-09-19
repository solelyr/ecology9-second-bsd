package com.engine.icbc.service.impl;

import com.engine.core.impl.Service;
import com.engine.icbc.cmd.QstackBillSyncCmd;
import com.engine.icbc.service.EnterpriseBillService;

/**
 * @DESCRIPTION:
 * @USER: solelyr
 * @DATE: 2026/9/18 11:42
 */
public class EnterpriseBillServiceImpl extends Service implements EnterpriseBillService {
    @Override
    public Boolean QstackBillSync(String dueDateBgn, String dueDateEnd) {
       return commandExecutor.execute(new QstackBillSyncCmd(dueDateBgn, dueDateEnd));
    }
}
