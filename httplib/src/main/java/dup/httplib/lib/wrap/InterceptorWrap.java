package dup.httplib.lib.wrap;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * InterceptorWrap 包裹类
 *
 * @author dupeng
 * @date 2018/3/19
 */

public abstract class InterceptorWrap implements Interceptor {
    @Override
    public final Response intercept(Chain chain) throws IOException {
        return interceptWrap(chain);
    }

    protected abstract Response interceptWrap(Chain chain) throws IOException;

}
