package com.sahil.models;

import org.springframework.data.domain.Page;

public class PageResponseDTO<T> extends Response{

	private Page<T> data;

	public Page<T> getData() {
		return data;
	}

	public void setData(Page<T> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "PageResponseDTO [data=" + data + ", getCode()=" + getCode() + ", getMessage()=" + getMessage() + "]";
	}

	

}
