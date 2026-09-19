package icbc;

import com.engine.util.BsdServiceUtil;
import org.junit.Test;
import base.BaseTest;
/**
 * @DESCRIPTION:
 * @USER: solelyr
 * @DATE: 2026/9/18 14:43
 */
public class QstackBillSyncCmdTest extends BaseTest {

    @Test
    public void test(){
        BsdServiceUtil.getIcbcService().qstackBillSync("2026-09-01","2027-09-01");
    }
}
