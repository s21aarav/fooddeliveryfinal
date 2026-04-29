// ==========================================
// Food Delivery App - Main JavaScript
// ==========================================

// Form validation
document.addEventListener('DOMContentLoaded', function() {
    const orderForm = document.getElementById('orderForm');
    
    if (orderForm) {
        orderForm.addEventListener('submit', function(e) {
            const orderId = document.getElementById('orderId').value.trim();
            const customerName = document.getElementById('customerName').value.trim();
            const restaurant = document.getElementById('restaurant').value.trim();
            const amount = parseFloat(document.getElementById('amount').value);
            const status = document.getElementById('status').value;

            // Validation
            if (!orderId) {
                alert('❌ Please enter Order ID');
                e.preventDefault();
                return false;
            }

            if (!customerName) {
                alert('❌ Please enter Customer Name');
                e.preventDefault();
                return false;
            }

            if (!restaurant) {
                alert('❌ Please enter Restaurant Name');
                e.preventDefault();
                return false;
            }

            if (!amount || amount <= 0) {
                alert('❌ Please enter a valid amount (greater than 0)');
                e.preventDefault();
                return false;
            }

            if (!status) {
                alert('❌ Please select an order status');
                e.preventDefault();
                return false;
            }

            // If all validations pass
            console.log('✅ Form is valid, submitting...');
            return true;
        });
    }
});

// Confirm delete
function confirmDelete() {
    return confirm('Are you sure you want to delete this order? This action cannot be undone.');
}

// Auto-hide alerts after 5 seconds
function autoHideAlerts() {
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.opacity = '0';
            alert.style.transition = 'opacity 0.5s ease';
            setTimeout(() => {
                alert.style.display = 'none';
            }, 500);
        }, 5000);
    });
}

// Initialize on page load
window.addEventListener('load', function() {
    autoHideAlerts();
});

// Currency formatter for amount display
function formatCurrency(amount) {
    return '₹' + parseFloat(amount).toFixed(2);
}

// Smooth scroll to top
function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    });
}

// Add scroll event listener for smooth behavior
document.addEventListener('scroll', function() {
    const scrollButton = document.querySelector('.scroll-to-top');
    if (scrollButton) {
        if (window.scrollY > 300) {
            scrollButton.style.display = 'block';
        } else {
            scrollButton.style.display = 'none';
        }
    }
});

// Console logging for debugging
console.log('🍔 Food Delivery Order Management System');
console.log('Application initialized successfully!');
