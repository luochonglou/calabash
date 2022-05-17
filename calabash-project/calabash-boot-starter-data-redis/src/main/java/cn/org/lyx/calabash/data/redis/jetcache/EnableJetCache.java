package cn.org.lyx.calabash.data.redis.jetcache;

import com.alicp.jetcache.anno.field.CreateCacheAnnotationBeanPostProcessor;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author trio
 * @date 2022/5/16
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({CacheConfiguration.class, CacheConfigSelector.class, CreateCacheAnnotationBeanPostProcessor.class})
public @interface EnableJetCache {
    boolean proxyTargetClass() default false;

    AdviceMode mode() default AdviceMode.PROXY;

    int order() default 2147483647;

    String[] basePackages();
}
