package br.com.folha.jsf.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="telefoneConverter")
public class TelefoneConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String newvalue) {
		StringBuilder builder = new StringBuilder(newvalue);

	    //Caracteres permitidos '0..9 '(' ')' ' '  e '-' 

	    boolean encontrouCaracterInvalido = false;
	    int i = 0;
	    while ( i < builder.length() && !encontrouCaracterInvalido) {
	      char c = builder.charAt(i);
	      if (Character.isDigit(c))
	    	  i++;
	      else if (Character.isDefined('('))
	          builder.deleteCharAt(i);
	      else if (Character.isDefined(')'))
	    	  builder.deleteCharAt(i);
	      else if (Character.isDefined('-'))
	    	  builder.deleteCharAt(i);
	      else if (Character.isDefined(' '))
	    	  builder.deleteCharAt(i);
	      else 
	    	  encontrouCaracterInvalido = true;
	    }
	    if (encontrouCaracterInvalido) {
	      FacesMessage message = new FacesMessage("Ocorreu um erro de conversão. ","Telefone inválido");
	      message.setSeverity(FacesMessage.SEVERITY_ERROR);
	      throw new ConverterException(message);
	    }
	    return builder.toString();
	  }

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		//11111111
	    //(11) 1111-1111 - como será exibido
	    String v = arg2.toString();
	    StringBuilder builder = new StringBuilder();
	    int tam = v.length();
	    for(int i = 0; i < tam; i++) {
	    	if(i==0)
	    		builder.append('(');
	    	else if (i == 2){
	    		builder.append(')');
	    		//builder.append(' ');
	    	}
	    	else if (i == 6)
	    		builder.append("-");
	    	
	    	if (i < 14)
	    		builder.append(v.charAt(i));
	    	else
	    		break;
	    }
	    return builder.toString();
	  }
}
