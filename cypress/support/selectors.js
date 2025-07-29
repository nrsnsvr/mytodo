// Centralized selectors for all tests
// Copy these selectors to your test files

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

// Export for reference
module.exports = selectors; 