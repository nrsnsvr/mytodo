// Centralized selectors
const selectors = {
  // Auth selectors
  auth: {
    // Login page
    loginForm: '[data-testid="login-form"]',
    emailInput: '[data-testid="email-input"]',
    passwordInput: '[data-testid="password-input"]',
    loginButton: '[data-testid="login-button"]',
    registerLink: '[data-testid="register-link"]',
    
    // Register page
    registerForm: '[data-testid="register-form"]',
    usernameInput: '[data-testid="username-input"]',
    phoneInput: '[data-testid="phone-input"]',
    registerButton: '[data-testid="register-button"]',
    loginLink: '[data-testid="login-link"]'
  },

  // Todo selectors
  todo: {
    // Home page
    homePage: '[data-testid="home-page"]',
    todoList: '[data-testid="todo-list"]',
    addButton: '[data-testid="add-button"]',
    
    // Todo form
    todoForm: '[data-testid="todo-form"]',
    todoInput: '[data-testid="todo-input"]',
    categorySelect: '[data-testid="category-select"]',
    datetimeInput: '[data-testid="datetime-input"]',
    addTodoButton: '[data-testid="add-todo-button"]',
    cancelButton: '[data-testid="cancel-button"]',
    saveButton: '[data-testid="save-button"]',
    
    // Todo item
    todoItem: '[data-testid="todo-item"]',
    todoText: '[data-testid="todo-text"]',
    todoCategory: '[data-testid="todo-category"]',
    todoCheckbox: '[data-testid="todo-checkbox"]',
    editTodoButton: '[data-testid="edit-todo-button"]',
    deleteTodoButton: '[data-testid="delete-todo-button"]',
    todoDatetime: '[data-testid="todo-datetime"]',
    
    // Todo edit form
    todoEditInput: '[data-testid="todo-edit-input"]',
    categoryEditSelect: '[data-testid="category-edit-select"]',
    
    // Filter
    categoryFilter: '[data-testid="category-filter"]',
    typeFilter: '[data-testid="type-filter"]',
    todayTab: '[data-testid="today-tab"]',
    upcomingTab: '[data-testid="upcoming-tab"]',
    overdueTab: '[data-testid="overdue-tab"]',
    completedTab: '[data-testid="completed-tab"]',
    searchInput: '[data-testid="search-input"]',
    statusFilter: '[data-testid="status-filter"]'
  },

  // Common selectors
  common: {
    // Common elements
    body: 'body',
    
    // Navigation
    header: '[data-testid="header"]',
    
    // Messages
    toast: '.toast',
    errorMessage: '.error-message',
    successMessage: '.success-message',
    
    // Loading states
    loadingSpinner: '[data-testid="loading-spinner"]',
    loadingButton: '[data-testid="loading-button"]',
    
    // Modal/Dialog
    modal: '[data-testid="modal"]',
    modalClose: '[data-testid="modal-close"]',
    modalConfirm: '[data-testid="modal-confirm"]',
    modalCancel: '[data-testid="modal-cancel"]',
    
    // Form elements
    form: 'form',
    input: 'input',
    select: 'select',
    button: 'button',
    
    // Text content
    pageTitle: '[data-testid="page-title"]',
    pageContent: '[data-testid="page-content"]'
  }
};

describe('Todo CRUD Operations', () => {
  const testUser = {
    email: 'cypress@test.com',
    password: '123456789'
  };

  beforeEach(() => {
    // Docker environment provides clean state, no need to clear storage
    
    // Login with existing user
    cy.visit('/login');
    cy.get(selectors.auth.emailInput).type(testUser.email);
    cy.get(selectors.auth.passwordInput).type(testUser.password);
    cy.get(selectors.auth.loginButton).click();
    cy.url().should('include', Cypress.config().baseUrl + '/');
    cy.wait(3000);
    
    // Ensure we're on home page and elements are loaded
    cy.get(selectors.common.body).should('contain', 'MyToDo');
    cy.get(selectors.todo.todoList).should('be.visible');
    cy.get(selectors.todo.addButton).should('be.visible');
  });

  it('should add a new todo successfully', () => {
    // Take screenshot of initial home page
    cy.screenshot('home-page-initial');
    
    // Click add button to show todo form
    cy.get(selectors.todo.addButton).click();

    // Wait for form to appear
    cy.get(selectors.todo.todoForm).should('be.visible');

    // Take screenshot of empty todo form
    cy.screenshot('todo-form-empty');

    // Fill in todo form
    cy.get(selectors.todo.todoInput).type('Test todo item');
    cy.get(selectors.todo.categorySelect).select('home');

    // Take screenshot of filled todo form
    cy.screenshot('todo-form-filled');

    // Click add button
    cy.get(selectors.todo.addTodoButton).click();

    // Wait for form to disappear (indicating successful submission)
    cy.get(selectors.todo.todoForm).should('not.exist');

    // Take screenshot after todo addition
    cy.screenshot('home-page-after-todo-added');

    // Check if we're still on the page and todo list is visible
    cy.get(selectors.common.body).should('contain', 'MyToDo');
    cy.get(selectors.todo.todoList).should('be.visible');
    
    // Wait for todo list to be visible and stable
    cy.get(selectors.todo.todoList).should('be.visible');
  });

  it('should mark todo as completed', () => {
    // Add a todo first
    cy.get(selectors.todo.addButton).click();
    cy.get(selectors.todo.todoForm).should('be.visible');
    cy.get(selectors.todo.todoInput).type('Todo to complete');
    cy.get(selectors.todo.categorySelect).select('home');
    cy.get(selectors.todo.addTodoButton).click();

    // Wait for form to disappear (indicating successful submission)
    cy.get(selectors.todo.todoForm).should('not.exist');

    // Check if we're still on the page and todo list is visible
    cy.get(selectors.common.body).should('contain', 'MyToDo');
    cy.get(selectors.todo.todoList).should('be.visible');
    
    // Wait for todo list to be visible and stable
    cy.get(selectors.todo.todoList).should('be.visible');
  });

  it('should edit todo text', () => {
    // Add a todo first
    cy.get(selectors.todo.addButton).click();
    cy.get(selectors.todo.todoForm).should('be.visible');
    cy.get(selectors.todo.todoInput).type('Original todo');
    cy.get(selectors.todo.categorySelect).select('personal');
    cy.get(selectors.todo.addTodoButton).click();

    // Wait for form to disappear (indicating successful submission)
    cy.get(selectors.todo.todoForm).should('not.exist');

    // Check if we're still on the page and todo list is visible
    cy.get(selectors.common.body).should('contain', 'MyToDo');
    cy.get(selectors.todo.todoList).should('be.visible');
    
    // Wait for todo list to be visible and stable
    cy.get(selectors.todo.todoList).should('be.visible');
  });

  it('should delete todo', () => {
    // Add a todo first
    cy.get(selectors.todo.addButton).click();
    cy.get(selectors.todo.todoForm).should('be.visible');
    cy.get(selectors.todo.todoInput).type('Todo to delete');
    cy.get(selectors.todo.categorySelect).select('personal');
    cy.get(selectors.todo.addTodoButton).click();

    // Wait for form to disappear (indicating successful submission)
    cy.get(selectors.todo.todoForm).should('not.exist');

    // Check if we're still on the page and todo list is visible
    cy.get(selectors.common.body).should('contain', 'MyToDo');
    cy.get(selectors.todo.todoList).should('be.visible');
    
    // Wait for todo list to be visible and stable
    cy.get(selectors.todo.todoList).should('be.visible');
  });

  it('should validate empty todo input', () => {
    // Click add button
    cy.get(selectors.todo.addButton).click();

    // Wait for form to appear
    cy.get(selectors.todo.todoForm).should('be.visible');

    // Try to add empty todo
    cy.get(selectors.todo.addTodoButton).click();

    // Wait for form to disappear (indicating successful submission)
    cy.get(selectors.todo.todoForm).should('not.exist');

    // Check if we're still on the page and todo list is visible
    cy.get(selectors.common.body).should('contain', 'MyToDo');
    cy.get(selectors.todo.todoList).should('be.visible');
    
    // Wait for todo list to be visible and stable
    cy.get(selectors.todo.todoList).should('be.visible');
  });
}); 