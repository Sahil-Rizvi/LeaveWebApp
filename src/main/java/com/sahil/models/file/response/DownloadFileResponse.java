package com.sahil.models.file.response;

import java.nio.file.Path;
import java.util.Arrays;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;

import com.sahil.models.Response;

public class DownloadFileResponse extends Response{

	private Path path;
    
    private byte[] data;
    
    private ByteArrayResource resource;

    private MediaType mediaType;
    
	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public ByteArrayResource getResource() {
		return resource;
	}

	public void setResource(ByteArrayResource resource) {
		this.resource = resource;
	}
	

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	@Override
	public String toString() {
		return "DownloadFileResponse [path=" + path + ", data=" + Arrays.toString(data) + ", resource=" + resource
				+ ", mediaType=" + mediaType + "]";
	}

}
