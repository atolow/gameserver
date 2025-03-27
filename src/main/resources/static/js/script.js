document.addEventListener('DOMContentLoaded', () => {
    const sections = document.querySelectorAll('section');
    const navLinks = document.querySelectorAll('nav a[data-page]');

    function showSection(id) {
        sections.forEach(section => {
            section.classList.remove('active');
        });
        document.getElementById(id).classList.add('active');
    }

    function navigateTo(page) {
        showSection(page);
        // Update URL without reloading
        history.pushState(null, null, `#${page}`);
    }

    function handleNavigation(event) {
        event.preventDefault();
        const page = this.dataset.page;
        navigateTo(page);
    }

    // Initial setup
    if (window.location.hash) {
        showSection(window.location.hash.substring(1));
    } else {
        showSection('home');
    }

    // Set up navigation links
    navLinks.forEach(link => {
        link.addEventListener('click', handleNavigation);
    });

    // Handle back/forward button navigation
    window.addEventListener('popstate', () => {
        if (window.location.hash) {
            showSection(window.location.hash.substring(1));
        }
    });

    // Expose navigateTo function globally
    window.navigateTo = navigateTo;
});