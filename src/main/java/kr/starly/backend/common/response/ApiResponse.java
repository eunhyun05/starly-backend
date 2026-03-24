package kr.starly.backend.common.response;

public record ApiResponse<T>(
        String code,
        T data
) {

    public static <T> ApiResponse<T> success(SuccessCode successCode, T data) {
        return new ApiResponse<>(successCode.name(), data);
    }

    public static ApiResponse<Void> success(SuccessCode successCode) {
        return new ApiResponse<>(successCode.name(), null);
    }

    public static ApiResponse<Void> error(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.name(), null);
    }
}