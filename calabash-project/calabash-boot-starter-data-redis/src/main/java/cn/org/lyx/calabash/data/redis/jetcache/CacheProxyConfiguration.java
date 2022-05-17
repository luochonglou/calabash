package cn.org.lyx.calabash.data.redis.jetcache;

import com.alicp.jetcache.anno.aop.CacheAdvisor;
import com.alicp.jetcache.anno.aop.JetCacheInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author trio
 * @date 2022/5/16
 */

@Configuration
public class CacheProxyConfiguration implements ImportAware, ApplicationContextAware {
    protected AnnotationAttributes enableMethodCache;
    private ApplicationContext applicationContext;

    public CacheProxyConfiguration() {
    }

    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.enableMethodCache = AnnotationAttributes.fromMap(importMetadata.getAnnotationAttributes(EnableJetCache.class.getName(), false));
        if (this.enableMethodCache == null) {
            throw new IllegalArgumentException("@EnableJetCache is not present on importing class " + importMetadata.getClassName());
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean(
            name = {"jetcache2.internalCacheAdvisor"}
    )
    @Role(2)
    public CacheAdvisor jetcacheAdvisor(JetCacheInterceptor jetCacheInterceptor) {
        CacheAdvisor advisor = new CacheAdvisor();
        advisor.setAdviceBeanName("jetcache2.internalCacheAdvisor");
        advisor.setAdvice(jetCacheInterceptor);
        advisor.setBasePackages(this.enableMethodCache.getStringArray("basePackages"));
        advisor.setOrder((Integer) this.enableMethodCache.getNumber("order"));
        return advisor;
    }

    @Bean
    @Role(2)
    public JetCacheInterceptor jetCacheInterceptor() {
        return new JetCacheInterceptor();
    }
}
