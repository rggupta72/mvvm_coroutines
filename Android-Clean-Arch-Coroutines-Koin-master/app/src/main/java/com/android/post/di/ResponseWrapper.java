package com.android.post.di;

import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


public class ResponseWrapper extends Converter.Factory {


    protected static Class<?> getRawType(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            if (!(rawType instanceof Class)) throw new IllegalArgumentException();
            return (Class<?>) rawType;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        if (isWrapped(type)) {
            return null;
        }
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == ExcludeWrapper.class) {
                return null;
            }
        }
        Type envelopedType = TypeToken.getParameterized(BaseResponse.class, type).getType();
        final Converter<ResponseBody, BaseResponse<?>> delegate =
                retrofit.nextResponseBodyConverter(this, envelopedType, annotations);
        return (Converter<ResponseBody, Object>) value -> {
            BaseResponse<?> baseResponse = delegate.convert(value);
            String code = "ErrorOccurred";
            String message = "ErrorOccurred";
            if (baseResponse != null) {
                if (baseResponse.isSuccessful()) {
                    return baseResponse.result;
                }
                code = baseResponse.getMessage();
                message = baseResponse.getMessage();
            }
            throw new ServiceFailureException(code, message);
        };
    }

    private boolean isWrapped(Type type) {
        if (type instanceof ParameterizedType) {
            Class<?> rawType = getRawType(type);
            return (rawType == BaseResponse.class);
        }
        return false;
    }
}
