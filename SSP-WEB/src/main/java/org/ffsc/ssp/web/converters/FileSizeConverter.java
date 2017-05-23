package org.ffsc.ssp.web.converters;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="fileSizeConverter")
public class FileSizeConverter implements Converter {

	private static final String BYTES = " Bytes";
	private static final String KILO_BYTES = " KB";
	private static final String MEGA_BYTES = " MB";
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return Long.valueOf(value.replaceAll("[^0-9]", ""));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		BigDecimal valueInBytes = new BigDecimal(String.valueOf(value));
		
		//The value comes in bytes so ...
		if(valueInBytes.compareTo(new BigDecimal("1024")) >= 0){
			BigDecimal valueInKBytes = valueInBytes.divide(new BigDecimal("1024"))
													.setScale(2, RoundingMode.HALF_EVEN);
			
			return valueInKBytes.toPlainString().concat(KILO_BYTES);
		}
		
		if(valueInBytes.compareTo(new BigDecimal("1048576")) >= 0){
			BigDecimal valueInMBytes = valueInBytes.divide(new BigDecimal("1048576"))
													.setScale(2, RoundingMode.HALF_EVEN);
			
			return valueInMBytes.toPlainString().concat(MEGA_BYTES);
		}
					
		return valueInBytes.toPlainString().concat(BYTES);
	}
}