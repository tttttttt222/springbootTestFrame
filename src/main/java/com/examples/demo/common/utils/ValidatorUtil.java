package com.examples.demo.common.utils;

import lombok.extern.slf4j.Slf4j;
import net.sf.oval.Validator;

/**
 * @Author:
 * @Date: 2019/6/25
 */
@Slf4j
public class ValidatorUtil {
    private final static Validator validator = new Validator();

    //处理明文校验
//    public static Response<Boolean> validateObject(Object obj) {
//        Response<Boolean> response = new Response<>();
//        try {
//
//            List<ConstraintViolation> validateRes = validator.validate(obj);
//            if (validateRes != null && !validateRes.isEmpty()) {
//                response.setCode(validateRes.get(0).getErrorCode());
//                response.setMessage(validateRes.get(0).getMessage());
//                return response;
//            }
//            response.setResult(true);
//            return response;
//        } catch (Exception e) {
//            log.error("明文参数格式异常", e);
//            response.setCode(MemberCodeEnum.FAIL_GET_PARAMS.getCode());
//            response.setMessage(MemberCodeEnum.FAIL_GET_PARAMS.getDesc());
//            return response;
//        }
//    }
}
