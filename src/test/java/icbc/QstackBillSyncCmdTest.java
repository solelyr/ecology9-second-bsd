package icbc;

import org.junit.Test;
import base.BaseTest;
import com.engine.common.util.ServiceUtil;
import com.engine.icbc.service.EnterpriseBillService;
import com.engine.icbc.service.impl.EnterpriseBillServiceImpl;

/**
 * @DESCRIPTION:
 * @USER: solelyr
 * @DATE: 2026/9/18 14:43
 */
public class QstackBillSyncCmdTest extends BaseTest {
    private static EnterpriseBillService getService(){
        return ServiceUtil.getService(EnterpriseBillServiceImpl.class);
    }

    @Test
    public void test(){
        getService().QstackBillSync("2026-09-01","2027-09-01");
    }
}
