// Common selectors used across multiple pages
export const commonSelectors = {
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
}; 