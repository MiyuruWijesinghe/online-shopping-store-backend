package com.spm.onlineshopping.core;

import java.lang.reflect.Field;
import java.util.ArrayList;

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
import com.spm.onlineshopping.resource.CartAddResource;
import com.spm.onlineshopping.resource.CartListResource;
import com.spm.onlineshopping.resource.CategoryResource;
import com.spm.onlineshopping.resource.ItemAttributeValueResource;
import com.spm.onlineshopping.resource.ItemResource;
import com.spm.onlineshopping.resource.OrdersAddResource;
import com.spm.onlineshopping.resource.OrdersListResource;
import com.spm.onlineshopping.resource.OrdersUpdateResource;
import com.spm.onlineshopping.resource.PaymentRequestResource;
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
			case "ordersAddResource": 
				OrdersAddResource ordersAddResource = new OrdersAddResource();
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                    fieldName=error.getField();
                    if(fieldName.startsWith("orders")) {
                         fieldName=fieldName.replace("orders", "");
                             atPoint = fieldName.indexOf(']');
                             index=Integer.parseInt(fieldName.substring(1, atPoint));
                             fieldName=fieldName.substring(atPoint+2);
                             for (int i=0; i<=index; i++) {
                                 if(ordersAddResource.getOrders()==null || ordersAddResource.getOrders().isEmpty()) {
                                	 ordersAddResource.setOrders(new ArrayList<OrdersListResource>());
                                	 ordersAddResource.getOrders().add(i, new OrdersListResource());
                                 }else{
                                     if((ordersAddResource.getOrders().size()-1)<i) {
                                    	 ordersAddResource.getOrders().add(i, new OrdersListResource());
                                     }
                                 }
                             }
                             sField=ordersAddResource.getOrders().get(index).getClass().getDeclaredField(fieldName);
                             sField.setAccessible(true);
                             sField.set(ordersAddResource.getOrders().get(index), error.getDefaultMessage());
                    }else {
                        sField =  ordersAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(ordersAddResource.getClass().cast(ordersAddResource), error.getDefaultMessage());
                    }
                }
                return new ResponseEntity<>(ordersAddResource, HttpStatus.UNPROCESSABLE_ENTITY);	
			case "ordersUpdateResource":
				OrdersUpdateResource ordersUpdateResource = new OrdersUpdateResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					sField = ordersUpdateResource.getClass().getDeclaredField(error.getField());
					sField.setAccessible(true);
					sField.set(ordersUpdateResource.getClass().cast(ordersUpdateResource), error.getDefaultMessage());
				}
				return new ResponseEntity<>(ordersUpdateResource, HttpStatus.UNPROCESSABLE_ENTITY);	
			case "paymentRequestResource":
				PaymentRequestResource paymentRequestResource = new PaymentRequestResource();
				for (FieldError error : ex.getBindingResult().getFieldErrors()) {
					sField = paymentRequestResource.getClass().getDeclaredField(error.getField());
					sField.setAccessible(true);
					sField.set(paymentRequestResource.getClass().cast(paymentRequestResource), error.getDefaultMessage());
				}
				return new ResponseEntity<>(paymentRequestResource, HttpStatus.UNPROCESSABLE_ENTITY);
			case "cartAddResource": 
				CartAddResource cartAddResource = new CartAddResource();
                for (FieldError error : ex.getBindingResult().getFieldErrors()) {
                    fieldName=error.getField();
                    if(fieldName.startsWith("cartItems")) {
                         fieldName=fieldName.replace("cartItems", "");
                             atPoint = fieldName.indexOf(']');
                             index=Integer.parseInt(fieldName.substring(1, atPoint));
                             fieldName=fieldName.substring(atPoint+2);
                             for (int i=0; i<=index; i++) {
                                 if(cartAddResource.getCartItems()==null || cartAddResource.getCartItems().isEmpty()) {
                                	 cartAddResource.setCartItems(new ArrayList<CartListResource>());
                                	 cartAddResource.getCartItems().add(i, new CartListResource());
                                 }else{
                                     if((cartAddResource.getCartItems().size()-1)<i) {
                                    	 cartAddResource.getCartItems().add(i, new CartListResource());
                                     }
                                 }
                             }
                             sField=cartAddResource.getCartItems().get(index).getClass().getDeclaredField(fieldName);
                             sField.setAccessible(true);
                             sField.set(cartAddResource.getCartItems().get(index), error.getDefaultMessage());
                    }else {
                        sField =  cartAddResource.getClass().getDeclaredField(error.getField());
                        sField.setAccessible(true);
                        sField.set(cartAddResource.getClass().cast(cartAddResource), error.getDefaultMessage());
                    }
                }
                return new ResponseEntity<>(cartAddResource, HttpStatus.UNPROCESSABLE_ENTITY);
				
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
