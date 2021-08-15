package com.spm.onlineshopping.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.spm.onlineshopping.enums.TransferType;
import com.spm.onlineshopping.exception.CodeUniqueException;
import com.spm.onlineshopping.exception.InvalidDetailListServiceIdException;
import com.spm.onlineshopping.exception.InvalidServiceIdException;
import com.spm.onlineshopping.exception.NoRecordFoundException;
import com.spm.onlineshopping.exception.UserNotFoundException;
import com.spm.onlineshopping.exception.ValidateRecordException;
import com.spm.onlineshopping.resource.AttributeResource;
import com.spm.onlineshopping.resource.AttributeValueResource;
import com.spm.onlineshopping.resource.BrandResource;
import com.spm.onlineshopping.resource.CategoryResource;
import com.spm.onlineshopping.resource.ItemAddResource;
import com.spm.onlineshopping.resource.ItemAttributeValueAddResource;
import com.spm.onlineshopping.resource.ItemAttributeValueUpdateResource;
import com.spm.onlineshopping.resource.ItemUpdateResource;
import com.spm.onlineshopping.resource.SuccessAndErrorDetailsResource;
import com.spm.onlineshopping.resource.ValidateResource;


@RestControllerAdvice
public class BaseResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private Environment environment;
	
	
	@Override 
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) { 
		SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
		successAndErrorDetailsDataObject.setMessages(environment.getProperty("common.invalid-url-pattern"));
		successAndErrorDetailsDataObject.setDetails(ex.getMessage());
		return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({MethodArgumentTypeMismatchException.class})
	public ResponseEntity<Object> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
		SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
		successAndErrorDetailsDataObject.setMessages(environment.getProperty("common.argument-type-mismatch"));
		successAndErrorDetailsDataObject.setDetails(ex.getMessage());
		return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({UserNotFoundException.class})
	public ResponseEntity<Object> userNotFoundException(UserNotFoundException ex, WebRequest request) {
		SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
		successAndErrorDetailsResource.setMessages(environment.getProperty("common.user-not-found"));
		return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler({CodeUniqueException.class})
	public ResponseEntity<Object> codeUniqueException(CodeUniqueException ex, WebRequest request) {
		SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
		successAndErrorDetailsDataObject.setMessages(environment.getProperty("common.code-duplicate"));
		return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler({NoRecordFoundException.class})
	public ResponseEntity<Object> noRecordFoundException(NoRecordFoundException ex, WebRequest request) {
		SuccessAndErrorDetailsResource successAndErrorDetailsDataObject = new SuccessAndErrorDetailsResource();
		successAndErrorDetailsDataObject.setMessages(environment.getProperty("common.record-not-found"));
		return new ResponseEntity<>(successAndErrorDetailsDataObject, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		try {
			Field sField = null;
			String fieldName = null;
			Integer index = null;
			Integer index1 = null;
			Integer atPoint = null;
			String className = ex.getBindingResult().getObjectName();
			
			switch (className) {

			case "categoryResource":
				CategoryResource categoryResource = new CategoryResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					sField = categoryResource.getClass().getDeclaredField(error.getField());
					sField.setAccessible(true);
					sField.set(categoryResource.getClass().cast(categoryResource), error.getDefaultMessage());
				}
				return new ResponseEntity<>(categoryResource, HttpStatus.UNPROCESSABLE_ENTITY);
			case "brandResource":
				BrandResource brandResource = new BrandResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					sField = brandResource.getClass().getDeclaredField(error.getField());
					sField.setAccessible(true);
					sField.set(brandResource.getClass().cast(brandResource), error.getDefaultMessage());
				}
				return new ResponseEntity<>(brandResource, HttpStatus.UNPROCESSABLE_ENTITY);
			case "attributeResource":
				AttributeResource attributeResource = new AttributeResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					sField = attributeResource.getClass().getDeclaredField(error.getField());
					sField.setAccessible(true);
					sField.set(attributeResource.getClass().cast(attributeResource), error.getDefaultMessage());
				}
				return new ResponseEntity<>(attributeResource, HttpStatus.UNPROCESSABLE_ENTITY);
			case "attributeValueResource":
				AttributeValueResource attributeValueResource = new AttributeValueResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					sField = attributeValueResource.getClass().getDeclaredField(error.getField());
					sField.setAccessible(true);
					sField.set(attributeValueResource.getClass().cast(attributeValueResource), error.getDefaultMessage());
				}
				return new ResponseEntity<>(attributeValueResource, HttpStatus.UNPROCESSABLE_ENTITY);	
			case "itemAddResource": 
				ItemAddResource itemAddResource = new ItemAddResource();
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                    fieldName=error.getField();
                    if(fieldName.startsWith("itemAttributes")) {
                         fieldName=fieldName.replace("itemAttributes", "");
                             atPoint = fieldName.indexOf(']');
                             index=Integer.parseInt(fieldName.substring(1, atPoint));
                             fieldName=fieldName.substring(atPoint+2);
                             for (int i=0; i<=index; i++) {
                                 if(itemAddResource.getItemAttributes()==null || itemAddResource.getItemAttributes().isEmpty()) {
                                	 itemAddResource.setItemAttributes(new ArrayList<ItemAttributeValueAddResource>());
                                	 itemAddResource.getItemAttributes().add(i, new ItemAttributeValueAddResource());
                                 }else{
                                     if((itemAddResource.getItemAttributes().size()-1)<i) {
                                    	 itemAddResource.getItemAttributes().add(i, new ItemAttributeValueAddResource());
                                     }
                                 }
                             }
                             sField=itemAddResource.getItemAttributes().get(index).getClass().getDeclaredField(fieldName);
                             sField.setAccessible(true);
                             sField.set(itemAddResource.getItemAttributes().get(index), error.getDefaultMessage());
                    }else {
                        sField =  itemAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(itemAddResource.getClass().cast(itemAddResource), error.getDefaultMessage());
                    }
                }
                return new ResponseEntity<>(itemAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
        	case "itemUpdateResource": 
        		ItemUpdateResource itemUpdateResource = new ItemUpdateResource();
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                    fieldName=error.getField();
                    if(fieldName.startsWith("itemAttributes")) {
                         fieldName=fieldName.replace("itemAttributes", "");
                             atPoint = fieldName.indexOf(']');
                             index=Integer.parseInt(fieldName.substring(1, atPoint));
                             fieldName=fieldName.substring(atPoint+2);
                             for (int i=0; i<=index; i++) {
                                 if(itemUpdateResource.getItemAttributes()==null || itemUpdateResource.getItemAttributes().isEmpty()) {
                                	 itemUpdateResource.setItemAttributes(new ArrayList<ItemAttributeValueUpdateResource>());
                                	 itemUpdateResource.getItemAttributes().add(i, new ItemAttributeValueUpdateResource());
                                 }else{
                                     if((itemUpdateResource.getItemAttributes().size()-1)<i) {
                                    	 itemUpdateResource.getItemAttributes().add(i, new ItemAttributeValueUpdateResource());
                                     }
                                 }
                             }
                             sField=itemUpdateResource.getItemAttributes().get(index).getClass().getDeclaredField(fieldName);
                             sField.setAccessible(true);
                             sField.set(itemUpdateResource.getItemAttributes().get(index), error.getDefaultMessage());
                    }else {
                        sField =  itemUpdateResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(itemUpdateResource.getClass().cast(itemUpdateResource), error.getDefaultMessage());
                    }
                }
                return new ResponseEntity<>(itemUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);	
				
			default:
				return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
			}
		} catch (Exception e) {
			SuccessAndErrorDetailsResource successAndErrorDetailsResource = new SuccessAndErrorDetailsResource();
			successAndErrorDetailsResource.setMessages(environment.getProperty("common.internal-server-error"));
			successAndErrorDetailsResource.setDetails(e.getMessage());
			return new ResponseEntity<>(successAndErrorDetailsResource, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ExceptionHandler({InvalidServiceIdException.class})
	public ResponseEntity<Object> invalidServiceIdException(InvalidServiceIdException ex, WebRequest request) {
		ValidateResource validateResource=new ValidateResource();
		switch(ex.getServiceEntity()) 
        { 
            case USER_ID:
            	validateResource.setUserId(ex.getMessage());
            	break;
            default: 
 
        } 
        return new ResponseEntity<>(validateResource, HttpStatus.UNPROCESSABLE_ENTITY);
    }

	@ExceptionHandler({ValidateRecordException.class})
	public ResponseEntity<Object> validateRecordException(ValidateRecordException ex, WebRequest request) {
		try {
			ValidateResource typeValidation = new ValidateResource();		
			Class validationDetailClass = typeValidation.getClass();
			Field sField = validationDetailClass.getDeclaredField(ex.getField());
			sField.setAccessible(true);
			sField.set(typeValidation, ex.getMessage());		
			return new ResponseEntity<>(typeValidation, HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			SuccessAndErrorDetailsResource successAndErrorDetailsDto = new SuccessAndErrorDetailsResource();
			successAndErrorDetailsDto.setMessages(environment.getProperty("common.internal-server-error"));
			successAndErrorDetailsDto.setDetails(e.getMessage());
			return new ResponseEntity<>(successAndErrorDetailsDto, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ExceptionHandler({InvalidDetailListServiceIdException.class})
	public ResponseEntity<Object> invalidDetailListServiceIdException(InvalidDetailListServiceIdException ex, WebRequest request) {
		if(ex.getTransferType().equals(TransferType.ITEM_ATTRIBUTE_VALUE_SAVE)) {
			ItemAddResource itemAddResource = validateItemAddResource(ex);
			return new ResponseEntity<>(itemAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}else if(ex.getTransferType().equals(TransferType.ITEM_ATTRIBUTE_VALUE_UPDATE)) {
			ItemUpdateResource itemUpdateResource = validateItemUpdateResource(ex);
			return new ResponseEntity<>(itemUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}else {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	private ItemAddResource validateItemAddResource(InvalidDetailListServiceIdException ex) {
		ItemAddResource itemAddResources = new ItemAddResource();
		List<ItemAttributeValueAddResource> itemAttributeValueAddResource=new ArrayList<>();
		Integer index=ex.getIndex();
		for(int i=0;i<=ex.getIndex();i++){  
			itemAttributeValueAddResource.add(i, new ItemAttributeValueAddResource());
		}
		switch(ex.getServiceEntity()) 
        {
	        case ATTRIBUTE_VALUE_ID:
	        	itemAttributeValueAddResource.get(index).setAttributeValueId(ex.getMessage());
	            break;     
            default:       	
        }
		itemAddResources.setItemAttributes(itemAttributeValueAddResource);
		return itemAddResources;
	}
	
	private ItemUpdateResource validateItemUpdateResource(InvalidDetailListServiceIdException ex) {
		ItemUpdateResource itemUpdateResources = new ItemUpdateResource();
		List<ItemAttributeValueUpdateResource> itemAttributeValueUpdateResource=new ArrayList<>();
		Integer index=ex.getIndex();
		for(int i=0;i<=ex.getIndex();i++){  
			itemAttributeValueUpdateResource.add(i, new ItemAttributeValueUpdateResource());
		}
		switch(ex.getServiceEntity()) 
        {
	        case ID:
	        	itemAttributeValueUpdateResource.get(index).setId(ex.getMessage());
	            break;
	        case ATTRIBUTE_VALUE_ID:
	        	itemAttributeValueUpdateResource.get(index).setAttributeValueId(ex.getMessage());
	            break;   
            default:  	
        }
		itemUpdateResources.setItemAttributes(itemAttributeValueUpdateResource);
		return itemUpdateResources;
	}
	
}
