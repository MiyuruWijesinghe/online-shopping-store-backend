package com.spm.onlineshopping.core;

import java.lang.reflect.Field;

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

import com.spm.onlineshopping.exception.CodeUniqueException;
import com.spm.onlineshopping.exception.InvalidServiceIdException;
import com.spm.onlineshopping.exception.NoRecordFoundException;
import com.spm.onlineshopping.exception.UserNotFoundException;
import com.spm.onlineshopping.exception.ValidateRecordException;
import com.spm.onlineshopping.resource.AttributeResource;
import com.spm.onlineshopping.resource.AttributeValueResource;
import com.spm.onlineshopping.resource.BrandResource;
import com.spm.onlineshopping.resource.CategoryResource;
import com.spm.onlineshopping.resource.ItemAttributeValueResource;
import com.spm.onlineshopping.resource.ItemResource;
import com.spm.onlineshopping.resource.SignupRequestResource;
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
			case "itemResource":
				ItemResource itemResource = new ItemResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					sField = itemResource.getClass().getDeclaredField(error.getField());
					sField.setAccessible(true);
					sField.set(itemResource.getClass().cast(itemResource), error.getDefaultMessage());
				}
				return new ResponseEntity<>(itemResource, HttpStatus.UNPROCESSABLE_ENTITY);
			case "itemAttributeValueResource":
				ItemAttributeValueResource itemAttributeValueResource = new ItemAttributeValueResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					sField = itemAttributeValueResource.getClass().getDeclaredField(error.getField());
					sField.setAccessible(true);
					sField.set(itemAttributeValueResource.getClass().cast(itemAttributeValueResource), error.getDefaultMessage());
				}
				return new ResponseEntity<>(itemAttributeValueResource, HttpStatus.UNPROCESSABLE_ENTITY);	
			case "signupRequestResource":
				SignupRequestResource signupRequestResource = new SignupRequestResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					sField = signupRequestResource.getClass().getDeclaredField(error.getField());
					sField.setAccessible(true);
					sField.set(signupRequestResource.getClass().cast(signupRequestResource), error.getDefaultMessage());
				}
				return new ResponseEntity<>(signupRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);
				
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
	
}
