package com.artreset.image.exception;

/**
 * Image를 찾을 수 없는 경우 발생
 * @author Taehyun Jung
 */
public class ImageNotFoundException extends Exception {

    public ImageNotFoundException(String message) {
        super(message);
    }

}
