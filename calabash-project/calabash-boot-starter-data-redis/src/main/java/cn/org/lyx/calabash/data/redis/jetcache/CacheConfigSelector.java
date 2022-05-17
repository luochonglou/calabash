package cn.org.lyx.calabash.data.redis.jetcache;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.AutoProxyRegistrar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author trio
 * @date 2022/5/16
 */
public class CacheConfigSelector extends AdviceModeImportSelector<EnableJetCache> {
    public CacheConfigSelector() {
    }

    public String[] selectImports(AdviceMode adviceMode) {
        switch (adviceMode) {
            case PROXY:
                return this.getProxyImports();
            case ASPECTJ:
            default:
                return null;
        }
    }

    private String[] getProxyImports() {
        List<String> result = new ArrayList();
        result.add(AutoProxyRegistrar.class.getName());
        result.add(CacheProxyConfiguration.class.getName());
        return (String[]) result.toArray(new String[result.size()]);
    }
}