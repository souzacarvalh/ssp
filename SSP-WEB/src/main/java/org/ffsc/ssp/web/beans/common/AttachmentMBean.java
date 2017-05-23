package org.ffsc.ssp.web.beans.common;

import static org.ffsc.ssp.web.beans.common.SSPMessage.ATTACH_CONVERSION_FAILED;
import static org.ffsc.ssp.web.beans.common.SSPMessage.ATTACH_DOWNLOAD_FAILED;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.ffsc.ssp.service.domain.Attachment;
import org.ffsc.ssp.web.util.FacesContextHelper;
import org.ffsc.ssp.web.util.MessageProvider;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@ViewScoped
public class AttachmentMBean {

	private static final int DEFAULT_BUFFER_SIZE   = 10240;
	private static final String DEFAULT_EMPTY_MIME = "application/octet-stream";
	
	private MessageProvider messageProvider;

	private List<UploadedFile> uploads = new ArrayList<>();

	public void onUpload(FileUploadEvent event) {
		uploads.add(event.getFile());
	}
	
	public List<UploadedFile> getUploads() {
		return uploads;
	}

	public List<Attachment> getUploadsAsAttachment() {

		List<Attachment> attachments = new ArrayList<>();

		try {
			
			for (UploadedFile upload : uploads) {

				Attachment attachment = new Attachment();

				attachment.setFilename(normalizeFileName(upload.getFileName()));
				attachment.setStream(IOUtils.toByteArray(upload.getInputstream()));
				attachments.add(attachment);
			}

		} catch (IOException e) {
			FacesContextHelper.showMessageDialog(FacesMessage.SEVERITY_ERROR,
					messageProvider.getValue(ATTACH_CONVERSION_FAILED));
		}

		return attachments;
	}
	
	public void download(Attachment attachment) {	
		
		BufferedInputStream attachmentInputStream = 
				new BufferedInputStream(new ByteArrayInputStream(attachment.getStream()), DEFAULT_BUFFER_SIZE);
		
		BufferedOutputStream responseOutputStream = null;
		
        try {
			
            FacesContext facesContext = FacesContext.getCurrentInstance();
             
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
             
            response.reset();
            response.setHeader("Content-Length", String.valueOf(attachment.getSize()));  
            response.setHeader("Content-Disposition", "attachment;filename=\""  + attachment.getFilename() + "\"");  
            response.setHeader("Content-Type", resolveMIME(attachment.getStream()));

            //Read the stream content and write to response output ...
        	responseOutputStream = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);  
        	byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];  
        	int length;  
        	while ((length = attachmentInputStream.read(buffer)) > 0) {  
        		responseOutputStream.write(buffer, 0, length);  
        	} 
        	
		    responseOutputStream.flush();
		    
		    facesContext.responseComplete();
		    
		} catch (Exception e) {
		
			FacesContextHelper.showMessageDialog(FacesMessage.SEVERITY_ERROR,
					messageProvider.getValue(ATTACH_DOWNLOAD_FAILED));
		
		} finally {
			try {
				if(attachmentInputStream != null){
					attachmentInputStream.close();
				}
				if(responseOutputStream != null){
					responseOutputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
	
	private String resolveMIME(byte[] bytes) throws Exception{
		MagicMatch match = Magic.getMagicMatch(bytes);	
		return StringUtils.isNotEmpty(match.getMimeType()) ? match.getMimeType(): DEFAULT_EMPTY_MIME;
	}
	
	/**
	 * Normalize File Names with default server encoding
	 * 
	 * This method fix File Upload Bug with character encoding
	 * Prime Faces uses the platform default character encoding for form fields instead of 
	 * the one set in the HTTP servlet request.
	 **/
	public String normalizeFileName(String utf8String){
		try {
			return new String(utf8String.getBytes(Charset.defaultCharset()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return utf8String;
		}
	}
	
	@Inject
	public void setMessageProvider(MessageProvider messageProvider) {
		this.messageProvider = messageProvider;
	}
}