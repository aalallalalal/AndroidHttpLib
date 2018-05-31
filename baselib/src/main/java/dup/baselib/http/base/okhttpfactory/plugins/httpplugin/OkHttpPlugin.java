package dup.baselib.http.base.okhttpfactory.plugins.httpplugin;

import android.support.annotation.Nullable;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import dup.baselib.http.base.okhttpfactory.plugins.httpplugin.okhttplib.OkClientManager;
import dup.baselib.http.base.okhttpfactory.plugins.httpplugin.okhttplib.OkHttpInitManager;
import dup.baselib.http.base.okhttpfactory.plugins.httpplugin.okhttplib.OkHttpWrapCallback;
import dup.baselib.http.interfaces.OnceSetting;
import dup.baselib.http.interfaces.ProgressCallBack;
import dup.baselib.http.interfaces.RequestCallBack;
import dup.baselib.http.interfaces.plugins.InterHttpPlugin;
import dup.baselib.log.LogOperate;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Okhttp网络请求功能 插件。
 * 一次性TimeoutSetting 不建议大量使用，否则会微影响性能。常用超时时间应调用初始化功能插件 来初始化超时时间。
 *
 * @author dupeng
 * @date 2018/2/23
 */

public final class OkHttpPlugin implements InterHttpPlugin<Call, ResponseBody, Call> {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    /**
     * 文件上传需要的配置参数
     */
    private static final MediaType MEDIA_OBJECT_STREAM = MediaType.parse("application/octet-stream");

    private static OkHttpPlugin INSTANCE;

    public static OkHttpPlugin getInstance() {
        if (INSTANCE == null) {
            synchronized (OkHttpPlugin.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OkHttpPlugin();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * get网络请求
     *
     * @param url         请求地址
     * @param params      请求参数
     * @param callBack    请求回调
     * @param onceSetting 单独设置此次请求的参数.可为空。
     * @return 请求
     */
    @Override
    public Call get(String url, Map<String, Object> params, RequestCallBack<Call, ResponseBody> callBack, OnceSetting... onceSetting) {
        OkHttpClient okHttpClient = null;
        Request request = null;
        try {
            okHttpClient = applyChangedSetting(onceSetting);
            request = new Request.Builder().get().url(builderGetUrl(url, params)).build();
        } catch (Exception e) {
            LogOperate.e(e.getMessage());
        }
        return requestCall(okHttpClient, request, callBack);
    }

    /**
     * post网络请求
     *
     * @param url         请求地址
     * @param params      请求参数
     * @param callBack    请求回调
     * @param onceSetting 单独设置此次请求的参数.可为空。
     * @return 请求
     */
    @Override
    public Call post(String url, Map<String, Object> params, RequestCallBack<Call, ResponseBody> callBack, OnceSetting... onceSetting) {
        OkHttpClient okHttpClient = null;
        Request request = null;
        try {
            okHttpClient = applyChangedSetting(onceSetting);
            request = new Request.Builder().post(builderData(params, onceSetting)).url(url).build();
        } catch (Exception e) {
            LogOperate.e(e.getMessage());
        }
        return requestCall(okHttpClient, request, callBack);
    }

    /**
     * put网络请求
     *
     * @param url         请求地址
     * @param params      请求参数
     * @param callBack    请求回调
     * @param onceSetting 单独设置此次请求的参数.可为空。
     * @return 请求
     */
    @Override
    public Call put(String url, Map<String, Object> params, RequestCallBack<Call, ResponseBody> callBack, OnceSetting... onceSetting) {
        OkHttpClient okHttpClient = null;
        Request request = null;
        try {
            okHttpClient = applyChangedSetting(onceSetting);
            request = new Request.Builder().put(builderData(params, onceSetting)).url(url).build();
        } catch (Exception e) {
            LogOperate.e(e.getMessage());
        }
        return requestCall(okHttpClient, request, callBack);
    }

    /**
     * delete网络请求
     *
     * @param url         请求地址
     * @param params      请求参数
     * @param callBack    请求回调
     * @param onceSetting 单独设置此次请求的参数.可为空。
     * @return 请求
     */
    @Override
    public Call delete(String url, Map<String, Object> params, RequestCallBack<Call, ResponseBody> callBack, OnceSetting... onceSetting) {
        OkHttpClient okHttpClient = null;
        Request request = null;
        try {
            okHttpClient = applyChangedSetting(onceSetting);
            request = new Request.Builder().delete(builderData(params, onceSetting)).url(url).build();
        } catch (Exception e) {
            LogOperate.e(e.getMessage());
        }
        return requestCall(okHttpClient, request, callBack);
    }

    /**
     * patch网络请求
     *
     * @param url         请求地址
     * @param params      请求参数
     * @param callBack    请求回调
     * @param onceSetting 单独设置此次请求的参数.可为空。
     * @return 请求
     */
    @Override
    public Call patch(String url, Map<String, Object> params, RequestCallBack<Call, ResponseBody> callBack, OnceSetting... onceSetting) {
        OkHttpClient okHttpClient = null;
        Request request = null;
        try {
            okHttpClient = applyChangedSetting(onceSetting);
            request = new Request.Builder().patch(builderData(params, onceSetting)).url(url).build();
        } catch (Exception e) {
            LogOperate.e(e.getMessage());
        }
        return requestCall(okHttpClient, request, callBack);
    }

    /**
     * 文件上传 、多文件上传
     *
     * @param url         请求地址
     * @param params      请求参数，上传文件的话，value 为 File
     * @param callBack    请求回调
     * @param onceSetting 单独设置此次请求的参数.可为空。
     * @return 请求
     */
    @Override
    public Call upLoad(String url, Map<String, Object> params, RequestCallBack<Call, ResponseBody> callBack, @Nullable ProgressCallBack progressCallBack, OnceSetting... onceSetting) {
        if (params == null) {
            LogOperate.e("上传文件请求参数为空！");
            return null;
        }
        OkHttpClient okHttpClient = null;
        Request request = null;
        try {
            okHttpClient = applyChangedSetting(onceSetting);
            request = new Request.Builder().post(builderMultiData(params, progressCallBack)).url(url).build();
        } catch (Exception e) {
            LogOperate.e(e.getMessage());
        }
        return requestCall(okHttpClient, request, callBack);
    }

    /**
     * post文件下载。
     *
     * @param url         请求地址
     * @param params      请求参数
     * @param callBack    请求回调
     * @param onceSetting 单独设置此次请求的参数.可为空。
     * @return 请求
     */
    @Override
    public Call downLoadWithPost(String url, Map<String, Object> params, RequestCallBack<Call, ResponseBody> callBack, OnceSetting... onceSetting) {
        OkHttpClient okHttpClient = null;
        Request request = null;
        try {
            okHttpClient = applyChangedSetting(onceSetting);
            request = new Request.Builder().post(builderFormData(params)).url(url).build();
        } catch (Exception e) {
            LogOperate.e(e.getMessage());
        }
        return requestCall(okHttpClient, request, callBack);
    }

    /**
     * get文件下载。
     *
     * @param url         请求地址
     * @param params      请求参数
     * @param callBack    请求回调
     * @param onceSetting 单独设置此次请求的参数.可为空。
     * @return 请求
     */
    @Override
    public Call downLoadWithGet(String url, Map<String, Object> params, RequestCallBack<Call, ResponseBody> callBack, OnceSetting... onceSetting) {
        OkHttpClient okHttpClient = null;
        Request request = null;
        try {
            okHttpClient = applyChangedSetting(onceSetting);
            request = new Request.Builder().get().url(builderGetUrl(url, params)).build();
        } catch (Exception e) {
            LogOperate.e(e.getMessage());
        }
        return requestCall(okHttpClient, request, callBack);
    }

    /**
     * 调用call，发起异步请求。
     *
     * @param okHttpClient 请求client
     * @param request      请求数据
     * @param callBack     回调
     * @return 请求
     */
    private Call requestCall(OkHttpClient okHttpClient, Request request, RequestCallBack<Call, ResponseBody> callBack) {
        if (okHttpClient == null || request == null) {
            LogOperate.e("请求Request为空！");
            if (callBack != null) {
                callBack.onFailure(null, "请求Request为空！");
                callBack.onFinish(null, -1);
            }
            return null;
        }
        Call call = null;
        try {
            call = okHttpClient.newCall(request);
            if (callBack != null) {
                callBack.onStart(call);
            }
            call.enqueue(new OkHttpWrapCallback(callBack));
        } catch (Exception e) {
            LogOperate.e(e.getMessage());
        }
        return call;
    }

    /**
     * 根据是否存在 临时设置 参数，来返回不同的OkhttpClient。
     *
     * @param onceSetting 一次性超时设置实体类
     * @return OkhttpClient
     */
    private OkHttpClient applyChangedSetting(OnceSetting[] onceSetting) {
        if (onceSetting == null || onceSetting.length <= 0 || onceSetting[0] == null) {
            //没有设置临时参数。返回默认OkHttpClient
            return OkClientManager.getClient();
        } else {
            return OkClientManager.getClient(onceSetting);
        }
    }

    /**
     * 创建get 的url
     *
     * @param url
     * @param params
     * @return
     */
    private String builderGetUrl(String url, Map<String, Object> params) {
        try {
            if (params != null) {
                try {
                    StringBuffer stringBuffer = new StringBuffer(url);
                    stringBuffer.append("?");
                    for (Map.Entry<String, Object> entry : params.entrySet()) {
                        if (entry != null) {
                            stringBuffer.append(entry.getKey());
                            stringBuffer.append("=");
                            stringBuffer.append(entry.getValue());
                            stringBuffer.append("&");
                        }
                    }
                    url = stringBuffer.substring(0, stringBuffer.length() - 1);
                } catch (Exception e) {
                    LogOperate.e(e.getMessage());
                }
            }
        } catch (Exception e) {
            LogOperate.e(e.getMessage());
        }
        return url;
    }

    /**
     * 创建带文件 请求参数
     *
     * @param params
     * @param progressCallBack
     * @return
     */
    private RequestBody builderMultiData(Map<String, Object> params, ProgressCallBack progressCallBack) {
        RequestBody body = null;
        try {
            MultipartBody.Builder builder = new MultipartBody.Builder();
            //设置类型
            builder.setType(MultipartBody.FORM);
            //追加参数
            for (String key : params.keySet()) {
                Object object = params.get(key);
                if (object == null) {
                    continue;
                }
                if (!(object instanceof File)) {
                    builder.addFormDataPart(key, object.toString());
                } else {
                    File file = (File) object;
                    if (file == null) {
                        LogOperate.w("上传文件为空");
                        continue;
                    }
                    if (!file.exists() || file.isDirectory()) {
                        LogOperate.w("上传文件不存在，或是文件夹");
                        continue;
                    }
                    if (progressCallBack == null) {
                        builder.addFormDataPart(key, file.getName(), RequestBody.create(null, file));
                    } else {
                        builder.addFormDataPart(key, file.getName(), createProgressRequestBody(MEDIA_OBJECT_STREAM, file, progressCallBack));
                    }
                }
            }
            //创建RequestBody
            body = builder.build();
        } catch (Exception e) {
            LogOperate.e(e.getMessage());
        }
        return body;
    }

    private RequestBody builderData(Map<String, Object> params, OnceSetting[] onceSetting) {
        OnceSetting setting = null;
        if (onceSetting != null && onceSetting.length > 0 && onceSetting[0] != null) {
            setting = onceSetting[0];
        }
        //获取全局的请求类型
        MediaTypeWrap mediaTypeWrap = OkHttpInitManager.getMediaType();
        if (setting != null && setting.mediaTypeWrap != null) {
            mediaTypeWrap = setting.mediaTypeWrap;
        }

        RequestBody requestBody;
        switch (mediaTypeWrap) {
            case MEDIA_TYPE_FORM:
                requestBody = builderFormData(params);
                break;
            default:
                requestBody = builderRawData(params, mediaTypeWrap.getTypeStr());
                break;
        }
        return requestBody;
    }

    /**
     * 创建raw 请求数据
     *
     * @param params
     * @return
     */
    private RequestBody builderRawData(Map<String, Object> params, String typeStr) {
        JSONObject jsonObject = new JSONObject(params);
        String jsonObj = jsonObject.toString();

        RequestBody body = RequestBody.create(MediaType.parse(typeStr), jsonObj);
        return body;
    }

    /**
     * 创建form 请求数据
     *
     * @param params
     * @return
     */
    private RequestBody builderFormData(Map<String, Object> params) {
        FormBody.Builder builder = new FormBody.Builder();
        try {
            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    if (entry != null) {
                        if (entry.getValue() != null) {
                            builder.add(entry.getKey(), entry.getValue().toString());
                        } else {
                            builder.add(entry.getKey(), "");
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogOperate.e(e.getMessage());
        }
        return builder.build();
    }

    /**
     * 上传进度功能
     *
     * @param contentType
     * @param file
     * @param progressCallBack
     * @return
     */
    private RequestBody createProgressRequestBody(final MediaType contentType, final File file, final ProgressCallBack progressCallBack) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                if (file == null || !file.exists()) {
                    return 1;
                }
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source = null;
                try {
                    source = Okio.source(file);
                    Buffer buf = new Buffer();
                    long total = contentLength();
                    long current = 0;
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                        current += readCount;
                        if (progressCallBack != null) {
                            progressCallBack.onProgress(file, total, current);
                        }
                    }
                } catch (Exception e) {
                    LogOperate.e(e.getMessage());
                } finally {
                    if (source != null) {
                        source.close();
                    }
                }
            }
        };
    }
}
