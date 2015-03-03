package com.artreset.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 일반 DTO 클래스가 상속받아야 하는 Base DTO Class <br>
 * 여러 DTO에서 사용되는 공통 메서드는 이 클래스에 정의한다.
 * 
 * @author Taehyun Jung
 */
public abstract class BaseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
}