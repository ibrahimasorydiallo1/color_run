// Fonction pour initialiser la navigation
function initNavigation() {
    // Initialiser le menu mobile
    initMobileMenu();
    
    // Mettre à jour l'état de connexion
    updateAuthState();
    
    // Mettre à jour les liens actifs
    updateActiveLinks();
}

// Fonction pour initialiser le menu mobile
function initMobileMenu() {
    const mobileMenuButton = document.querySelector('.mobile-menu-button');
    const mobileMenuClose = document.querySelector('.mobile-menu-close');
    const mobileMenu = document.querySelector('.mobile-menu');
    
    if (mobileMenuButton && mobileMenuClose && mobileMenu) {
        mobileMenuButton.addEventListener('click', () => {
            mobileMenu.classList.add('active');
            document.body.style.overflow = 'hidden';
        });
        
        mobileMenuClose.addEventListener('click', () => {
            mobileMenu.classList.remove('active');
            document.body.style.overflow = '';
        });
        
        // Fermer le menu en cliquant sur un lien
        const mobileLinks = mobileMenu.querySelectorAll('a');
        mobileLinks.forEach(link => {
            link.addEventListener('click', () => {
                mobileMenu.classList.remove('active');
                document.body.style.overflow = '';
            });
        });
    }
}

// Fonction pour mettre à jour l'état d'authentification
function updateAuthState() {
    const user = JSON.parse(localStorage.getItem('user'));
    const authLinks = document.querySelectorAll('.auth-links');
    const mobileAuthLinks = document.querySelectorAll('.mobile-auth-links');
    const userMenu = document.querySelector('.user-menu');
    const mobileUserMenu = document.querySelector('.mobile-user-menu');
    
    if (user) {
        // Utilisateur connecté
        authLinks.forEach(container => {
            container.innerHTML = `
                <div class="relative">
                    <button class="flex items-center space-x-2 text-gray-700 hover:text-purple-600" aria-label="Menu utilisateur">
                        <img src="https://via.placeholder.com/32" alt="Avatar" class="w-8 h-8 rounded-full">
                        <span>${user.name}</span>
                        <i class="fas fa-chevron-down"></i>
                    </button>
                    <div class="absolute right-0 mt-2 w-48 bg-white rounded-md shadow-lg py-1 hidden">
                        <a href="/profile.html" class="block px-4 py-2 text-gray-700 hover:bg-gray-100">Profil</a>
                        <a href="/dashboard.html" class="block px-4 py-2 text-gray-700 hover:bg-gray-100">Tableau de bord</a>
                        <a href="/my-races.html" class="block px-4 py-2 text-gray-700 hover:bg-gray-100">Mes courses</a>
                        <a href="/favorites.html" class="block px-4 py-2 text-gray-700 hover:bg-gray-100">Favoris</a>
                        <a href="/settings.html" class="block px-4 py-2 text-gray-700 hover:bg-gray-100">Paramètres</a>
                        <button onclick="logout()" class="block w-full text-left px-4 py-2 text-gray-700 hover:bg-gray-100">Déconnexion</button>
                    </div>
                </div>
            `;
        });
        
        mobileAuthLinks.forEach(container => {
            container.innerHTML = `
                <div class="border-t border-gray-200 pt-4">
                    <div class="flex items-center space-x-3 mb-4">
                        <img src="https://via.placeholder.com/32" alt="Avatar" class="w-8 h-8 rounded-full">
                        <span class="font-semibold">${user.name}</span>
                    </div>
                    <a href="/profile.html" class="mobile-nav-link">Profil</a>
                    <a href="/dashboard.html" class="mobile-nav-link">Tableau de bord</a>
                    <a href="/my-races.html" class="mobile-nav-link">Mes courses</a>
                    <a href="/favorites.html" class="mobile-nav-link">Favoris</a>
                    <a href="/settings.html" class="mobile-nav-link">Paramètres</a>
                    <button onclick="logout()" class="w-full text-left mobile-nav-link text-red-600">Déconnexion</button>
                </div>
            `;
        });
    } else {
        // Utilisateur non connecté
        authLinks.forEach(container => {
            container.innerHTML = `
                <a href="/login.html" class="nav-link">Connexion</a>
                <a href="/register.html" class="nav-link">Inscription</a>
            `;
        });
        
        mobileAuthLinks.forEach(container => {
            container.innerHTML = `
                <a href="/login.html" class="mobile-nav-link">Connexion</a>
                <a href="/register.html" class="mobile-nav-link">Inscription</a>
            `;
        });
    }
}

// Fonction pour mettre à jour les liens actifs
function updateActiveLinks() {
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll('.nav-link');
    const mobileNavLinks = document.querySelectorAll('.mobile-nav-link');
    
    navLinks.forEach(link => {
        if (link.getAttribute('href') === currentPath) {
            link.classList.add('text-purple-600');
        } else {
            link.classList.remove('text-purple-600');
        }
    });
    
    mobileNavLinks.forEach(link => {
        if (link.getAttribute('href') === currentPath) {
            link.classList.add('text-purple-600');
        } else {
            link.classList.remove('text-purple-600');
        }
    });
}

// Fonction pour gérer la déconnexion
function logout() {
    localStorage.removeItem('user');
    localStorage.removeItem('favorites');
    localStorage.removeItem('registrations');
    window.location.href = '/';
}

// Initialiser la navigation au chargement de la page
document.addEventListener('DOMContentLoaded', initNavigation); 