package com.engine.util;

import com.engine.common.util.ServiceUtil;
import com.engine.module.erp.service.ErpService;
import com.engine.module.erp.service.impl.ErpServiceImpl;
import com.engine.module.icbc.service.IcbcService;
import com.engine.module.icbc.service.impl.IcbcServiceImpl;

/**
 * @DESCRIPTION:
 * @USER: solelyr
 * @DATE: 2026/9/19 22:41
 */
public class BsdServiceUtil {

    public static ErpService getErpService() {
        return ServiceUtil.getService(ErpServiceImpl.class);
    }

    public static IcbcService getIcbcService() {
        return ServiceUtil.getService(IcbcServiceImpl.class);
    }
}
