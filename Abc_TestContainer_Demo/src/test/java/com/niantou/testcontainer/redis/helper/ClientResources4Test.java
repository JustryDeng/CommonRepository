package com.niantou.testcontainer.redis.helper;

import com.niantou.testcontainer.author.JustryDeng;
import io.lettuce.core.RedisURI;
import io.lettuce.core.resource.DefaultClientResources;
import io.lettuce.core.resource.DnsResolver;
import io.lettuce.core.resource.DnsResolvers;
import io.lettuce.core.resource.SocketAddressResolver;
import org.springframework.context.annotation.Primary;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * ClientResources4Test
 *
 * @author {@link JustryDeng}
 * @since 2020/11/17 13:35:48
 */
@Primary
public class ClientResources4Test extends DefaultClientResources {
    
    protected ClientResources4Test() {
        super(DefaultClientResources.builder());
    }
    
    @Override
    public SocketAddressResolver socketAddressResolver() {
        return new SocketAddressResolver4Test(DnsResolvers.UNRESOLVED);
    }
    
    /**
     * SocketAddressResolver4Test
     *
     * @author {@link JustryDeng}
     * @since 2020/11/17 13:46:20
     */
    public static class SocketAddressResolver4Test extends SocketAddressResolver {
        
        protected SocketAddressResolver4Test(DnsResolver dnsResolver) {
            super(dnsResolver);
        }
        
        @Override
        public SocketAddress resolve(RedisURI redisURI) {
            return InetSocketAddress.createUnresolved("localhost", redisURI.getPort());
        }
    }
}
