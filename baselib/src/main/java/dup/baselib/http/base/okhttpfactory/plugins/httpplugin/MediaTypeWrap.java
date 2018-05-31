package dup.baselib.http.base.okhttpfactory.plugins.httpplugin;

/**
 * 请求类型
 *
 * @author dupeng
 * @date 2018/3/26
 */

public enum MediaTypeWrap {
    /**
     * application/x-www-form-urlencoded
     */
    MEDIA_TYPE_FORM("application/x-www-form-urlencoded; charset=utf-8"),
    /**
     * application/json
     */
    MEDIA_TYPE_RAW_JSON("application/json; charset=utf-8"),
    /**
     * text/plain
     */
    MEDIA_TYPE_RAW_TEXT("text/plain; charset=utf-8"),
    /**
     * application/javascript
     */
    MEDIA_TYPE_RAW_JAVASCRIPT("application/javascript; charset=utf-8"),
    /**
     * application/xml
     */
    MEDIA_TYPE_RAW_APPLICATION_XML("application/xml; charset=utf-8"),
    /**
     * text/xml
     */
    MEDIA_TYPE_RAW_TEXT_XML("text/xml; charset=utf-8"),
    /**
     * text/html
     */
    MEDIA_TYPE_RAW_TEXT_HTML("text/html; charset=utf-8");

    private final String typeStr;

    MediaTypeWrap(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getTypeStr() {
        return typeStr;
    }
}
