// Custom JavaScript for the Product Management System

// Wait for the DOM to be fully loaded
document.addEventListener('DOMContentLoaded', function() {
    // Initialize any Bootstrap tooltips
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
    
    // Auto-hide alerts after 5 seconds
    setTimeout(function() {
        var alerts = document.querySelectorAll('.alert-success, .alert-danger');
        alerts.forEach(function(alert) {
            // Create a Bootstrap alert instance and hide it
            var bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        });
    }, 5000);
    
    // Handle product deletion via AJAX
    var deleteButtons = document.querySelectorAll('.delete-product-btn');
    deleteButtons.forEach(function(button) {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            var productId = this.getAttribute('data-product-id');
            
            if (confirm('Are you sure you want to delete this product?')) {
                fetch('/api/products/' + productId, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json',
                        'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').getAttribute('content')
                    }
                })
                .then(function(response) {
                    if (response.ok) {
                        window.location.reload();
                    } else {
                        alert('Error deleting product. Please try again.');
                    }
                })
                .catch(function(error) {
                    console.error('Error:', error);
                    alert('Error deleting product. Please try again.');
                });
            }
        });
    });
    
    // Handle form validation
    var forms = document.querySelectorAll('.needs-validation');
    Array.prototype.slice.call(forms).forEach(function(form) {
        form.addEventListener('submit', function(event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });
});

// Function to handle product deletion (can be called from inline onclick handlers)
function deleteProduct(id) {
    if (confirm('Are you sure you want to delete this product?')) {
        fetch('/api/products/' + id, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').getAttribute('content')
            }
        })
        .then(function(response) {
            if (response.ok) {
                window.location.reload();
            } else {
                alert('Error deleting product. Please try again.');
            }
        })
        .catch(function(error) {
            console.error('Error:', error);
            alert('Error deleting product. Please try again.');
        });
    }
}