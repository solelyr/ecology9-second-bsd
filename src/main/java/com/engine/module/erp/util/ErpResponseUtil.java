package com.engine.module.erp.util;

import com.engine.module.erp.vo.ErpResponse;
import com.icbc.api.internal.util.internal.util.fastjson.JSON;
import com.icbc.api.internal.util.internal.util.fastjson.TypeReference;

/**
 * ERP 响应反序列化工具。
 */
public final class ErpResponseUtil {

    private ErpResponseUtil() {
    }

    /**
     * 将 ERP 返回的 JSON 解析为包含指定成功结果类型的响应对象。
     *
     * @param json ERP 返回的 JSON
     * @param resultType success 数组元素类型
     * @param <T> success 数组元素类型
     * @return ERP 响应对象
     */
    public static <T> ErpResponse<T> parse(String json, Class<T> resultType) {
        return JSON.parseObject(
                json,
                new TypeReference<ErpResponse<T>>(resultType) {}
        );
    }
}
