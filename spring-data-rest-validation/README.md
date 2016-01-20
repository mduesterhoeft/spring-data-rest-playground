# Bean Validation in spring-data-rest

## add bean validation to custom spring-data-rest controllers (`@RepositoryRestController`)
- spring-data-rest does do bean validation in controllers annotated with (`@RepositoryRestController`)
- to work around this we
 - put a `LocalValidatorFactoryBean` on the application context
```java
@Bean
	@Primary
	Validator validator() {
		return new LocalValidatorFactoryBean();
	}
```
 - in the custom controller wire this Validator and use `@InitBinder` to register the controller
```java
@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }
```
 - throw a `RepositoryConstraintViolationException` to get the normal spring-data-rest output
```java
@RequestMapping(path = "persons", method = POST)
    public ResponseEntity<Void> create(@Valid @RequestBody Person person, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new RepositoryConstraintViolationException(bindingResult);
        }
        //...
    }
```
 - use bean validation always before save and before create

-
