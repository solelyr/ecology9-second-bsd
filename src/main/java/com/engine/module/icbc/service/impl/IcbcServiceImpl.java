package com.engine.module.icbc.service.impl;

import com.engine.core.impl.Service;
import com.engine.module.icbc.cmd.QstackBillSyncCmd;
import com.engine.module.icbc.service.IcbcService;

/**
 * @DESCRIPTION:
 * @USER: solelyr
 * @DATE: 2026/9/18 11:42
 */
public class IcbcServiceImpl extends Service implements IcbcService {
    @Override
    public Boolean qstackBillSync(String dueDateBgn, String dueDateEnd) {
       return commandExecutor.execute(new QstackBillSyncCmd(dueDateBgn, dueDateEnd));
    }
}
