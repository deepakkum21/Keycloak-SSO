## Important thing:

### /sso/login needs to present in the valid redirect uri 
### and needs to be present in the  .antMatchers("/sso/login").permitAll() as it is for spring security and need to be bypassed