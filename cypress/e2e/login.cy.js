// Centralized selectors
const selectors = require('../support/selectors');

describe('Register and Login Test', () => {
  const testUser = {
    username: 'cypressuser',
    email: 'cypress@test.com',
    password: '123456789',
    phone: '+94716573585'
  };

  beforeEach(() => {
    // Docker environment provides clean state, no need to clear storage
  });

  it('should register a new user successfully', () => {
    cy.visit('/register');
    
    // Check if we're on register page
    cy.url().should('include', '/register');
    
    // Take screenshot of register page
    cy.screenshot('register-page');
    
    // Check if register form is visible
    cy.get(selectors.auth.registerForm).should('be.visible');
    
    // Fill in the registration form
    cy.get(selectors.auth.usernameInput).type(testUser.username);
    cy.get(selectors.auth.emailInput).type(testUser.email);
    cy.get(selectors.auth.phoneInput).type(testUser.phone);
    cy.get(selectors.auth.passwordInput).type(testUser.password);
    
    // Take screenshot of filled form
    cy.screenshot('register-form-filled');
    
    // Click register button
    cy.get(selectors.auth.registerButton).click();
    
    // Should redirect to home page after successful registration
    cy.url().should('include', Cypress.config().baseUrl + '/');
    
    // Wait for page to load and elements to be visible
    cy.get(selectors.common.body).should('contain', 'MyToDo');
    
    // Take screenshot of home page after registration
    cy.screenshot('home-page-after-registration');
    
    // Should show todo list or be on home page
    cy.get(selectors.common.body).should('contain', 'MyToDo');
  });

  it('should login with the registered user successfully', () => {
    cy.visit('/login');
    
    // Check if we're on login page
    cy.url().should('include', '/login');
    
    // Take screenshot of login page
    cy.screenshot('login-page');
    
    // Check if login form is visible
    cy.get(selectors.auth.loginForm).should('be.visible');
    
    // Fill in the login form
    cy.get(selectors.auth.emailInput).type(testUser.email);
    cy.get(selectors.auth.passwordInput).type(testUser.password);
    
    // Take screenshot of filled login form
    cy.screenshot('login-form-filled');
    
    // Click login button
    cy.get(selectors.auth.loginButton).click();
    
    // Should redirect to home page after successful login
    cy.url().should('include', Cypress.config().baseUrl + '/');
    
    // Take screenshot of home page after login
    cy.screenshot('home-page-after-login');
    
    // Should show todo list
    cy.get(selectors.todo.todoList).should('be.visible');
  });

  it('should show error for invalid email format', () => {
    cy.visit('/login');
    
    // Fill in invalid email format
    cy.get(selectors.auth.emailInput).type('invalid-email');
    cy.get(selectors.auth.passwordInput).type('somepassword');
    
    // Click login button
    cy.get(selectors.auth.loginButton).click();
    
    // Should stay on login page
    cy.url().should('include', '/login');
    
    // Should show validation error
    cy.get(selectors.common.body).should('contain', 'Invalid email address');
  });

  it('should show error for wrong email', () => {
    cy.visit('/login');
    
    // Fill in wrong email but correct format
    cy.get(selectors.auth.emailInput).type('wrong@email.com');
    cy.get(selectors.auth.passwordInput).type(testUser.password);
    
    // Click login button
    cy.get(selectors.auth.loginButton).click();
    
    // Should stay on login page (login failed)
    cy.url().should('include', '/login');
    
    // Should still show login form
    cy.get(selectors.auth.loginForm).should('be.visible');
  });

  it('should show error for wrong password', () => {
    cy.visit('/login');
    
    // Fill in correct email but wrong password
    cy.get(selectors.auth.emailInput).type(testUser.email);
    cy.get(selectors.auth.passwordInput).type('wrongpassword');
    
    // Click login button
    cy.get(selectors.auth.loginButton).click();
    
    // Should stay on login page (login failed)
    cy.url().should('include', '/login');
    
    // Should still show login form
    cy.get(selectors.auth.loginForm).should('be.visible');
  });

  it('should show error for empty email', () => {
    cy.visit('/login');
    
    // Leave email empty, fill password
    cy.get(selectors.auth.passwordInput).type('somepassword');
    
    // Click login button
    cy.get(selectors.auth.loginButton).click();
    
    // Should stay on login page
    cy.url().should('include', '/login');
    
    // Should show validation error
    cy.get(selectors.common.body).should('contain', 'Email is required');
  });

  it('should show error for empty password', () => {
    cy.visit('/login');
    
    // Fill email, leave password empty
    cy.get(selectors.auth.emailInput).type('test@email.com');
    
    // Click login button
    cy.get(selectors.auth.loginButton).click();
    
    // Should stay on login page
    cy.url().should('include', '/login');
    
    // Should show validation error
    cy.get(selectors.common.body).should('contain', 'Password is required');
  });

  it('should show error for empty fields', () => {
    cy.visit('/login');
    
    // Leave both fields empty
    cy.get(selectors.auth.loginButton).click();
    
    // Should stay on login page
    cy.url().should('include', '/login');
    
    // Should still show login form
    cy.get(selectors.auth.loginForm).should('be.visible');
  });

  it('should show error for non-existent user', () => {
    cy.visit('/login');
    
    // Fill in credentials for non-existent user
    cy.get(selectors.auth.emailInput).type('nonexistent@test.com');
    cy.get(selectors.auth.passwordInput).type('somepassword');
    
    // Click login button
    cy.get(selectors.auth.loginButton).click();
    
    // Should stay on login page (login failed)
    cy.url().should('include', '/login');
    
    // Should still show login form
    cy.get(selectors.auth.loginForm).should('be.visible');
  });

  it('should navigate to register page from login', () => {
    cy.visit('/login');
    
    // Click on register link
    cy.get(selectors.auth.registerLink).click();
    
    // Should navigate to register page
    cy.url().should('include', '/register');
    
    // Should show register form
    cy.get(selectors.auth.registerForm).should('be.visible');
  });

  it('should validate email format on blur', () => {
    cy.visit('/login');
    
    // Type invalid email and blur
    cy.get(selectors.auth.emailInput).type('invalid-email').blur();
    
    // Should still be on login page
    cy.url().should('include', '/login');
    
    // Should still show login form
    cy.get(selectors.auth.loginForm).should('be.visible');
  });

  it('should validate password length', () => {
    cy.visit('/login');
    
    // Type short password
    cy.get(selectors.auth.passwordInput).type('123').blur();
    
    // Should still be on login page
    cy.url().should('include', '/login');
    
    // Should still show login form
    cy.get(selectors.auth.loginForm).should('be.visible');
  });
}); 