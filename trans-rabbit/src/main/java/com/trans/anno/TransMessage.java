package com.trans.anno;

import java.lang.annotation.*;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-21
 * @Time: 14:57
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface TransMessage {

    /**
     * 交换机
     * @return
     */
    String exchange() default "";

    /**
     * bind的broker
     * @return
     */
    String bindingKey() default "";

    /**
     * 业务id
     * @return
     */
    String bussnessId() default "";
}
